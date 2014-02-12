define([
	'jquery',
	'backbone',
	'underscore',
	'jquery_ui'
], function( $, Backbone, _ ) {

var TextCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'textQuestion',
    template: _.template('\
    					<h3>Text Question</h3>\
    					<div>\
    						<p><textarea class="question" rows="2" cols="80" placeholder="Insert the question"></textarea></p>\
    						<p><textarea id="answer" rows="4" cols="80"></textarea></p>\
    					</div>'),
    render: function() {
        this.$el.html(this.template());
    }
});

var SingleCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'singleChoiceQuestion',
    template: _.template('\
						<h3>Single Choice Question</h3>\
						<div>\
							<p>\
								<textarea class="question" rows="2" cols="80" placeholder="Insert the question"></textarea>\
								<br><button id="addOption">Add Option</button>\
							</p>\
							<div class="single-options"></div>\
						</div>'),
    events: {
        "click #addOption" : "newOption"
    },
    render: function() {
        this.$el.html(this.template());
    },
    newOption: function( event ) {
        var singleOptionCreateView = new SingleOptionCreateView();
        singleOptionCreateView.render(this.cid);
        this.$el.find('.single-options').append(singleOptionCreateView.el);
    }
});

var MultipleCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'multipleChoiceQuestion',
    template: _.template('\
    					<h3>Multiple Choice Question</h3>\
    					<div>\
    						<p>\
    							<textarea class="question" rows="2" cols="80" placeholder="Insert the question"></textarea>\
    							<br><button id="addOption">Add Option</button>\
    						</p>\
    						<div class="multiple-options"></div>\
    					</div>'),
    events: {
        "click #addOption" : "newOption"
    },
    render: function() {
        this.$el.html(this.template());
    },
    newOption: function() {
        var multipleOptionCreateView = new MultipleOptionCreateView();
        multipleOptionCreateView.render();
        this.$el.find('.multiple-options').append(multipleOptionCreateView.el);
    }
});

var SingleOptionCreateView = Backbone.View.extend({
	tagName: 'li',
    className: 'singleChoiceOption',
    template: _.template('\
    					<input type="radio" name="<%= name %>">\
    					<input type="text" placeholder="Insert the option">\
    					'),
    render: function(cid) {
        this.$el.html(this.template({name: cid}));
        console.log(this.el);
    }
});

var MultipleOptionCreateView = Backbone.View.extend({
	tagName: 'li',
    className: 'multipleChoiceOption',
    template: _.template('\
    					<input type="checkbox">\
    					<input type="text" placeholder="Insert the option">\
    					'),
    
    render: function() {
        this.$el.html(this.template());
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