$(document).ready(function () {

    var slider = $('#slider');
    var ctrlNext = $('.ctrl_next');
    var ctrlPrev = $('.ctrl_prev');
    var list = slider.find('ul');
    var slidesCount = list.children().length;
    var sliderWidth = slider.width();
    var sliderUIWidth = slidesCount * sliderWidth;
    var moveDelay = 400;

    list.css({width: sliderUIWidth});

    ctrlNext.click(function () {
        if (slidesCount > 1)
            moveRight();
    });

    ctrlPrev.click(function () {
        if (slidesCount > 1)
            moveLeft();
    });

    function moveRight() {
        list.animate({
            left: -sliderWidth
        }, moveDelay, function () {
            list.children().first().appendTo(list);
            list.css('left', '0');
        });
    }

    function moveLeft() {
        list.children().last().prependTo(list);
        list.css('left', -sliderWidth);
        list.animate({
            left: 0
        }, moveDelay);
    }

});