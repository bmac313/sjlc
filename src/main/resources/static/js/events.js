$(document).ready(function() {

    /* .nav-boxes events*/
    var aboutOriginal = "<img src='/img/community_links.png'>";

    var aboutMouseOver = "<ul class='block-list'>" +
                         "<li><a href='/about/staff'>Staff</a></li>" +
                         "<li><a href='/about/beliefs'>What We Believe</a></li>" +
                         "<li><a href='/about/history'>Church History</a></li>" +
                         "</ul>";

    var preschoolOriginal = "<img src='/img/preschool.png'>";

    var preschoolMouseOver = "<div class='inner-nav-box'>" +
                             "<a href='/preschool' class='btn-primary btn-lg'>Learn More</a>" +
                             "</div>";

    var calendarOriginal = "<img src='/img/calendar.png'>";

    var calendarMouseOver = "<div class='inner-nav-box'>" +
                            "<a href='/calendar' class='btn-primary btn-lg'>Learn More</a>" +
                            "</div>";

    var sermonsOriginal = "<img src='/img/ministries.png'>";

    var sermonsMouseOver = "<ul class='block-list'>" +
                           "<li><a href='/downloads/sermons'>Sermons</a></li>" +
                           "<li><a href='/downloads/newsletter'>Newsletter</a></li>" +
                           "</ul>";


    $('#about-us').bind({
        mouseenter: function() {
            $('#about-us').addClass('nav-box-active');
            $('#about-us > img').replaceWith(aboutMouseOver);
        },
        mouseleave: function() {
            $('#about-us').removeClass('nav-box-active');
            $('#about-us > .block-list').replaceWith(aboutOriginal);
        }
    });

    $('#preschool').bind({
        mouseenter: function() {
            $('#preschool').addClass('nav-box-active');
            $('#preschool > img').replaceWith(preschoolMouseOver);
        },
        mouseleave: function() {
            $('#preschool').removeClass('nav-box-active');
            $('#preschool > .inner-nav-box').replaceWith(preschoolOriginal);
        }
    });

    $('#calendar').bind({
        mouseenter: function() {
            $('#calendar').addClass('nav-box-active');
            $('#calendar > img').replaceWith(calendarMouseOver);
        },
        mouseleave: function() {
            $('#calendar').removeClass('nav-box-active');
            $('#calendar > .inner-nav-box').replaceWith(calendarOriginal);
        }
    });

    $('#sermons-newsletter').bind({
        mouseenter: function() {
            $('#sermons-newsletter').addClass('nav-box-active');
            $('#sermons-newsletter > img').replaceWith(sermonsMouseOver);
        },
        mouseleave: function() {
            $('#sermons-newsletter').removeClass('nav-box-active');
            $('#sermons-newsletter > .block-list').replaceWith(sermonsOriginal);
        }
    });


});
