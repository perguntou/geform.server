package br.ufrj.softwaresmartphone.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import br.ufrj.softwaresmartphone.util.Options.ChoiceType;

/**
 * This class enables the conversion between a XML based stream
 * and a {@link Form} using the XmlPull API.
 * @see XmlPullParser
 * @see XmlSerializer
 * @see XmlPullParserFactory
 * @see InputStream
 * @see OutpuStream
 */
public final class FormXmlPull {

	private static final String ns = XmlPullParser.NO_NAMESPACE;
	private static final String encoding = "utf-8";

	/**
	 * Processes the content of the given {@link InputStream} instance as XML
	 * into a {@link Form} data structure.
	 * @param in InputStream containing the content the be parsed.
	 * @return the resultant form from parsing.
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static Form parse( InputStream in ) throws XmlPullParserException, IOException  {
		try {
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = parserFactory.newPullParser();
			parser.setFeature( XmlPullParser.FEATURE_PROCESS_NAMESPACES, false );
			parser.setInput( in, encoding );
			parser.nextTag();

			return readForm( parser );
		} finally {
			in.close();
		}
	}

	/**
	 * Processes the content from the specified {@link Form} instance into the given
	 * {@link OutputStream} instance as XML.
	 * @param form the form to be serialized.
	 * @param out target output stream.
	 * @throws XmlPullParserException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static void serialize( Form form, OutputStream out ) throws XmlPullParserException, IllegalArgumentException, IllegalStateException, IOException {
		try {
			XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
			parserFactory.setNamespaceAware( true );
			XmlSerializer serializer = parserFactory.newSerializer();
			serializer.setOutput( out, encoding );
			serializer.setFeature( "http://xmlpull.org/v1/doc/features.html#indent-output", true );

			serializer.startDocument( encoding, null );
			writeForm( form, serializer );
			serializer.endDocument();
		} finally {
			out.close();
		}
	}

	/**
	 * Internal method that parses the contents of a form.
	 * @param parser the responsible for parsing.
	 * @return the resultant form from parse.
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see XmlPullParser
	 * @see Form
	 */
	private static Form readForm( XmlPullParser parser ) throws XmlPullParserException, IOException {
		Form form = new Form();

		parser.require( XmlPullParser.START_TAG, ns, "form" );
		while( parser.next() != XmlPullParser.END_TAG ) {
			if( parser.getEventType() != XmlPullParser.START_TAG ) {
				continue;
			}
			String name = parser.getName();
			if( name.equals( "title" ) ) {
				form.setTitle( readText( parser ) );
			} else
			if( name.equals("item") ) {
				form.add( readItem( parser ) );
//			} else {
//				skip( parser );
			}
		}
		return form;
	}

	/**
	 * Internal method that serializes the contents of a form.
	 * @param form the form to be serialized.
	 * @param serializer the responsible for serialize.
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws XmlPullParserException
	 * @see Form
	 * @see XmlSerializer
	 */
	private static void writeForm( Form form, XmlSerializer serializer ) throws IllegalArgumentException, IllegalStateException, IOException, XmlPullParserException {
		serializer.startTag( ns, "form" );
		serializer.startTag( ns, "title" );
			serializer.text( form.title() );
		serializer.endTag( ns, "title" );
		for( Item item : form ) {
			writeItem( item, serializer );
		}
		serializer.endTag( ns, "form" );
	}

	/**
	 * Internal method that parses the contents of an form's item.
	 * @param parser the responsible for parsing.
	 * @return the resultant item from parse.
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see XmlPullParser
	 * @see Item
	 */
	private static Item readItem( XmlPullParser parser ) throws XmlPullParserException, IOException {
		Item item = new Item();

		parser.require( XmlPullParser.START_TAG, ns, "item" );
		while( parser.next() != XmlPullParser.END_TAG ) {
			if( parser.getEventType() != XmlPullParser.START_TAG ) {
				continue;
			}
			String name = parser.getName();
			if( name.equals( "question" ) ) {
				item.setQuestion( readText( parser ) );
			} else
			if( name.equals("options") ) {
				item.setOptions( readOptions( parser ) );
//			} else {
//				skip( parser );
			}
		}
		return item;
	}

	/**
	 * Internal method that serializes the contents of an form's item.
	 * @param item the item to be serialized.
	 * @param serializer the responsible for serialize.
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see Item
	 * @see XmlSerializer
	 */
	private static void writeItem( Item item, XmlSerializer serializer ) throws XmlPullParserException, IOException {
		serializer.startTag( ns, "item" );
		serializer.startTag( ns, "question" );
		serializer.text( item.getQuestion() );
		serializer.endTag( ns, "question" );
		if( item.hasOptions() ) {
			writeOptions( item.getOptions(), serializer );
		}
		serializer.endTag( ns, "item" );
	}

	/**
	 * Internal method that parses the contents of an item's options.
	 * @param parser the responsible for parsing.
	 * @return the resultant options from parse.
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see XmlPullParser
	 * @see Options
	 */
	private static Options readOptions( XmlPullParser parser ) throws XmlPullParserException, IOException {
		Options options = new Options();

		parser.require( XmlPullParser.START_TAG, ns, "options" );
		String att = parser.getAttributeValue( ns, "choice" );
		if( att.equals( "single" ) ) {
			options.setChoiceType( ChoiceType.single );
		} else
		if( att.equals( "multiple" ) ) {
			options.setChoiceType( ChoiceType.multiple );
		}
		String value = readText( parser ).trim();
		for( String option : value.split("#") ) {
			options.add( option );
		}
		parser.require(XmlPullParser.END_TAG, ns, "options");

		return options;
	}

	/**
	 * Internal method that serializes the contents of an item's options.
	 * @param options the options to be serialized.
	 * @param serializer the responsible for serialize.
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws IOException
	 * @see Options
	 * @see XmlSerializer
	 */
	private static void writeOptions( Options options, XmlSerializer serializer ) throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag( ns, "options" );
		serializer.attribute( ns, "choice", String.valueOf( options.getChoiceType() ) );
		StringBuilder strOptions = new StringBuilder();
		Iterator<String> it = options.iterator();
		strOptions.append( it.next() );
		while( it.hasNext() ) {
			strOptions.append( "#" + it.next() );
		}
		serializer.text( strOptions.toString() );
		serializer.endTag( ns, "options" );
	}

	/**
	 * Internal method that extracts text values.
	 * @param parser the responsible for parsing.
	 * @return the text extracted.
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @see XmlPullParser
	 */
	private static String readText( XmlPullParser parser ) throws XmlPullParserException, IOException {
		String text = "";
		if( parser.next() == XmlPullParser.TEXT ) {
			text = parser.getText();
			parser.nextTag();
		}
		return text;
	}

//	/**
//	 * Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
//	 * if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
//	 * finds the matching END_TAG (as indicated by the value of "depth" being 0).
//	 * @param parser XmlPullParser instance
//	 * @throws XmlPullParserException
//	 * @throws IOException
//	 */
//	private static void skip( XmlPullParser parser ) throws XmlPullParserException, IOException {
//		if( parser.getEventType() != XmlPullParser.START_TAG ) {
//			throw new IllegalStateException();
//		}
//		int depth = 1;
//		while( depth != 0 ) {
//			switch( parser.next() ) {
//			case XmlPullParser.END_TAG:
//					depth--;
//					break;
//			case XmlPullParser.START_TAG:
//					depth++;
//					break;
//			}
//		}
//	}

}
