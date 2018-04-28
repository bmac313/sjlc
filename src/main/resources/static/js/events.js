$(document).ready(function() {

    /* navbar events */
    if (document.getElementById("navbar")) { // If the navbar is present in the document
        window.onscroll = function() {stickNavBar()};
        var navbar = document.getElementById("navbar");
        var sticky = navbar.offsetTop;
    }

    function stickNavBar() {
        if (window.pageYOffset >= sticky) {
            navbar.classList.add("sticky");
        } else {
            navbar.classList.remove("sticky");
        }
    }


    /* .nav-boxes events */

    $('#about-us').bind({
        mouseenter: function() {
            $('#about-us').addClass('nav-box-active');
            $('#about-us > img').addClass('hidden');
            $('#about-us > .block-list').removeClass('hidden');
        },
        mouseleave: function() {
            $('#about-us').removeClass('nav-box-active');
            $('#about-us > img').removeClass('hidden');
            $('#about-us > .block-list').addClass('hidden');
        }
    });

    $('#preschool').bind({
        mouseenter: function() {
            $('#preschool').addClass('nav-box-active');
            $('#preschool > img').addClass('hidden');
            $('#preschool > .block-list').removeClass('hidden');
        },
        mouseleave: function() {
            $('#preschool').removeClass('nav-box-active');
            $('#preschool > img').removeClass('hidden');
            $('#preschool > .block-list').addClass('hidden');
        }
    });

    $('#calendar').bind({
        mouseenter: function() {
            $('#calendar').addClass('nav-box-active');
            $('#calendar > img').addClass('hidden');
            $('#calendar > .block-list').removeClass('hidden');
        },
        mouseleave: function() {
            $('#calendar').removeClass('nav-box-active');
            $('#calendar > img').removeClass('hidden');
            $('#calendar > .block-list').addClass('hidden');
        }
    });

    $('#sermons-newsletter').bind({
        mouseenter: function() {
            $('#sermons-newsletter').addClass('nav-box-active');
            $('#sermons-newsletter > img').addClass('hidden');
            $('#sermons-newsletter > .block-list').removeClass('hidden');
        },
        mouseleave: function() {
            $('#sermons-newsletter').removeClass('nav-box-active');
            $('#sermons-newsletter > img').removeClass('hidden');
            $('#sermons-newsletter > .block-list').addClass('hidden');
        }
    });

});
