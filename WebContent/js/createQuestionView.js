var TextCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'textQuestion',
    template: _.template('<h3>Text Question</h3><div><p><textarea id="question" rows="2" cols="80" placeholder="Insert the question" /></p><p><textarea id="answer" rows="4" cols="80"></textarea></p></div>'),
    
    render: function() {
        this.$el.html(this.template());
    }
});

var SingleCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'singleChoiceQuestion',
    template: _.template('<h3>Single Choice Question</h3><div><p><textarea id="question" rows="2" cols="80" placeholder="Insert the question" /></p><p><textarea id="answer" rows="4" cols="80"></textarea></p></div>'),
    
    render: function() {
        this.$el.html(this.template());
    }
});
var MultipleCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'multipleChoiceQuestion',
    template: _.template('<h3>Multiple Choice Question</h3><div><p><textarea id="question" rows="2" cols="80" placeholder="Insert the question" /></p><p><textarea id="answer" rows="4" cols="80"></textarea></p></div>'),
    
    render: function() {
        this.$el.html(this.template());
    }
});