$(document).ready(function() {
    initEvents();
});

$(window).resize(function() {
    rebindNavBoxEvents();
});

var initEvents = function() {
    bindHeaderEvents();
    bindNavBoxEvents();
    bindModalEvents();
}

var bindHeaderEvents = function() {
    $('#header-scroll-btn').click(function() {
        $('html,body').animate({
            scrollTop: $("#scroll-location").offset().top},
            'slow');
    });
}

var bindNavBoxEvents = function() {
    /* .nav-boxes events */
    if ($(window).width() >= 751) {
        $('#mobile-nav-instructions').addClass('hidden');
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
    } else {
        $('#mobile-nav-instructions').removeClass('hidden');
        $('#about-us').bind({
            click: function() {
                $('#about-us').toggleClass('nav-box-active');
                $('#about-us > img').toggleClass('hidden');
                $('#about-us > .block-list').toggleClass('hidden');
            }
        });

        $('#preschool').bind({
            click: function() {
                $('#preschool').toggleClass('nav-box-active');
                $('#preschool > img').toggleClass('hidden');
                $('#preschool > .block-list').toggleClass('hidden');
            }
        });

        $('#calendar').bind({
            click: function() {
                $('#calendar').toggleClass('nav-box-active');
                $('#calendar > img').toggleClass('hidden');
                $('#calendar > .block-list').toggleClass('hidden');
            }
        });

        $('#sermons-newsletter').bind({
            click: function() {
                $('#sermons-newsletter').toggleClass('nav-box-active');
                $('#sermons-newsletter > img').toggleClass('hidden');
                $('#sermons-newsletter > .block-list').toggleClass('hidden');
            }
        });
    }
};

var bindModalEvents = function() {
    $('.btn-remove-attendee').bind({
        click: function() {
           // Note: The confirm dialog is toggled on using the "data-toggle" property in view-event.html

           // Uncomment if you want the attendees modal to close then the dialog opens; BE SURE TO also uncomment
           //           the toggle in .btn-remove-attendee-cancel below to reopen the list when this dialog is closed!
           //$('#showAttendeesModal').modal('toggle');
        }
    });

    $('.btn-remove-attendee-cancel').bind({
        click: function() {
            // Note: The confirm dialog is toggled off using the "data-toggle" property in view-event.html

            // ONLY uncomment the below line if you are toggling off the attendees list upon opening this dialog (see above).
            //$('#showAttendeesModal').modal('toggle');

            $('#showAttendeesModal').css('overflow-y', 'auto'); // re-enable vertical scrolling on main modal
        }
    });
}

var rebindNavBoxEvents = function() {
    $('#about-us').unbind();
    $('#preschool').unbind();
    $('#calendar').unbind();
    $('#sermons-newsletter').unbind();
    bindNavBoxEvents();
};
