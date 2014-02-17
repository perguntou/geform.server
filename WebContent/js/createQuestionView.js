define([
	'jquery',
	'backbone',
	'underscore',
	'jquery_ui'
], function( $, Backbone, _ ) {

var TextCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'item',
    attributes: {type: "TEXT"},
    template: _.template('\
    					<h3 class="showQuestion">\
    						Text Question\
    						<a class="deleteItem" title="Delete this item"></a>\
    					</h3>\
    					<div>\
    						<p><textarea class="question" rows="2" cols="80" placeholder="Insert the question"></textarea></p>\
    						<p><textarea id="answer" rows="4" cols="80"></textarea></p>\
    					</div>'),
    events: {
        "click .deleteItem" : "deleteItem",
        "change .question" : "updateShowQuestion",
        "change" : "verify"
    },
    render: function() {
        this.$el.html(this.template());
    },
    deleteItem: function() {
        this.remove();
    },
    updateShowQuestion: function() {
    	var showQuestion = this.$(".question").val().trim() +
    						"<a class=\"deleteItem\" title=\"Delete this item\"></a>";
        this.$(".showQuestion").html( showQuestion );
        $('.questions').accordion("refresh");
    },
    verify: function() {
    	var ready = "<a class = \"itemReady\"></a>";
    	if ( this.$(".question").val().trim().length != 0 ) {
    		if ( this.$el.find(".itemReady").length == 0 ) {
    			this.$(".showQuestion").prepend( ready );
    	        $('.questions').accordion("refresh");
			}
    	} else {
    		this.$(".itemReady").remove();
    	}
    }
});

var SingleCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'item',
    attributes: {type: "SINGLE_CHOICE"},
    template: _.template('\
			    		<h3 class="showQuestion">\
							Single Choice Question\
							<a class="deleteItem" title="Delete this item"></a>\
						</h3>\
						<div>\
							<p>\
								<textarea class="question" rows="2" cols="80" placeholder="Insert the question"></textarea>\
								<br><button id="addOption">Add Option</button>\
							</p>\
							<div class="options"></div>\
						</div>'),
	events: {
	    "click #addOption" : "newOption",
	    "click .deleteItem" : "deleteItem",
        "change .question" : "updateShowQuestion",
        "change" : "verify"
	},
    render: function() {
        this.$el.html(this.template());
    },
    newOption: function( event ) {
        var singleOptionCreateView = new SingleOptionCreateView();
        singleOptionCreateView.render(this.cid);
        this.$el.find(".options").append(singleOptionCreateView.el);
    },
    deleteItem: function() {
        this.remove();
    },
    updateShowQuestion: function() {
    	var showQuestion = this.$(".question").val().trim() +
    						"<a class=\"deleteItem\" title=\"Delete this item\"></a>";
        this.$(".showQuestion").html( showQuestion );
        $('.questions').accordion("refresh");
    },
    //############## Ainda falta corrigir. Botões não fazer verify. #########
    verify: function() {
    	var ready = "<a class = \"itemReady\"></a>";
    	var complete = false;
    	if ( this.$(".question").val().trim().length != 0 ) {
    		var options = this.$el.find(".option");
    		if ( options.length > 1 ) {
    			var noEmpty = true;
    			$.each( options, function( index, option ){
    				if ( $(option).val().trim().length == 0 ){
    					noEmpty = false;
    				}
    			});
    			if ( noEmpty ) {
    				complete = true;
    			}
    		}
    	}
    	if ( complete ) {
    		if ( (this.$el.find(".itemReady").length == 0) ) {
    			this.$(".showQuestion").prepend( ready );
    	        $('.questions').accordion("refresh");
    		}
    	} else {
    		this.$(".itemReady").remove();
    	}
    }
});

var MultipleCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'item',
    attributes: {type: "MULTIPLE_CHOICE"},
    template: _.template('\
			    		<h3 class="showQuestion">\
							Multiple Choice Question\
							<a class="deleteItem" title="Delete this item"></a>\
						</h3>\
    					<div>\
    						<p>\
    							<textarea class="question" rows="2" cols="80" placeholder="Insert the question"></textarea>\
    							<br><button id="addOption">Add Option</button>\
    						</p>\
    						<div class="options"></div>\
    					</div>'),
    events: {
        "click #addOption" : "newOption",
        "click .deleteItem" : "deleteItem",
        "change .question" : "updateShowQuestion"
    },
    render: function() {
        this.$el.html(this.template());
    },
    newOption: function() {
        var multipleOptionCreateView = new MultipleOptionCreateView();
        multipleOptionCreateView.render();
        this.$el.find(".options").append(multipleOptionCreateView.el);
    },
    deleteItem: function() {
        this.remove();
    },
    updateShowQuestion: function() {
    	var showQuestion = this.$(".question").val().trim() +
    						"<a class=\"deleteItem\" title=\"Delete this item\"></a>";
        this.$(".showQuestion").html( showQuestion );
        $('.questions').accordion("refresh");
    }
});

var SingleOptionCreateView = Backbone.View.extend({
	tagName: 'li',
    className: 'singleChoiceOption',
    template: _.template('\
    					<input type="radio" name="<%= name %>">\
    					<input class="option" type="text" placeholder="Insert the option">\
    					<a class="deleteOption" title="Delete this option"></a>\
    					'),
    events: {
        "click .deleteOption": "deleteOption"
    },
    render: function(cid) {
        this.$el.html(this.template({name: cid}));
    },
    deleteOption: function() {
        this.remove();
    }
});

var MultipleOptionCreateView = Backbone.View.extend({
	tagName: 'li',
    className: 'multipleChoiceOption',
    template: _.template('\
    					<input type="checkbox">\
    					<input class="option" type="text" placeholder="Insert the option">\
    					<a class="deleteOption" title="Delete this option"></a>\
    					'),
     events: {
        "click .deleteOption": "deleteOption"
    },   
    render: function() {
        this.$el.html(this.template());
    },
    deleteOption: function() {
        this.remove();
    }
});

return {
	TextCreateView: TextCreateView,
	SingleCreateView: SingleCreateView,
	MultipleCreateView: MultipleCreateView,
	SingleOptionCreateView: SingleOptionCreateView,
	MultipleOptionCreateView: MultipleOptionCreateView
};

});