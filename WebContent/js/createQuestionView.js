var TextCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'textQuestion',
    template: _.template('<h3>Text Question</h3><div><p><textarea class="question" rows="2" cols="80" placeholder="Insert the question" /></p><p><textarea id="answer" rows="4" cols="80"></textarea></p></div>'),
    
    render: function() {
        this.$el.html(this.template());
    }
});

var SingleCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'singleChoiceQuestion',
    template: _.template('<h3>Single Choice Question</h3><div><p><textarea class="question" rows="2" cols="80" placeholder="Insert the question" /><br><button id="addOption">Add Option</button></p><div class="single-options"></div></div>'),
    events: {
        "click #addOption" : "newOption"
    },
    render: function() {
        this.$el.html(this.template());
    },
    newOption: function() {
        var singleOptionCreateView = new SingleOptionCreateView();
        singleOptionCreateView.render();
        $('.single-options').append(singleOptionCreateView.el);
    //##### ERRO => NÃO PODE  USAR A CLASSE, NEM ID FIXO, AGRUPA TODAS Single Choice Questions #####
    }
});

var MultipleCreateView = Backbone.View.extend({
    tagName: 'div',
    className: 'multipleChoiceQuestion',
    template: _.template('<h3>Multiple Choice Question</h3><div><p><textarea class="question" rows="2" cols="80" placeholder="Insert the question" /><br><button id="addOption">Add Option</button></p><div class="multiple-options"></div></div>'),
    events: {
        "click #addOption" : "newOption"
    },
    render: function() {
        this.$el.html(this.template());
    },
    newOption: function() {
        var multipleOptionCreateView = new MultipleOptionCreateView();
        multipleOptionCreateView.render();
        $('.multiple-options').append(multipleOptionCreateView.el);
      //##### ERRO => NÃO PODE  USAR A CLASSE, NEM ID FIXO, AGRUPA TODAS Multiple Choice Questions #####
    }
});

var SingleOptionCreateView = Backbone.View.extend({
	tagName: 'li',
    className: 'singleChoiceOption',
    template: _.template('<input type="radio" name="group1"><input type="text" placeholder="Insert the option">'),
    //##### ERRO => NÃO PODE DEIXAR O NAME FIXO, AGRUPA TODOS RADIO`S #####
    render: function() {
        this.$el.html(this.template());
    }
});

var MultipleOptionCreateView = Backbone.View.extend({
	tagName: 'li',
    className: 'multipleChoiceOption',
    template: _.template('<input type="checkbox"><input type="text" placeholder="Insert the option">'),
    
    render: function() {
        this.$el.html(this.template());
    }
});