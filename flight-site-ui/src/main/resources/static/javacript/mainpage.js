document.addEventListener('DOMContentLoaded', function() {
    var backgroundImages = [
        'url(../aircraftimages/bg1.jpg)',
        'url(../aircraftimages/bg2.jpg)',
        'url(../aircraftimages/bg3.jpg)',
        'url(../aircraftimages/bg4.jpg)',
        'url(../aircraftimages/bg5.jpg)',
        'url(../aircraftimages/bg6.jpg)',
        'url(../aircraftimages/bg7.jpg)',
        'url(../aircraftimages/bg8.jpg)',
        'url(../aircraftimages/bg9.jpg)',
        'url(../aircraftimages/bg10.jpg)'
    ];

    let currentBackground = 0;
    const background1 = document.getElementById('background1');
    const background2 = document.getElementById('background2');

    // Preload images
    backgroundImages.forEach(function(img) {
        var image = new Image();
        image.src = img.slice(4, -1);
    });

    function changeBackground() {
        currentBackground = (currentBackground + 1) % backgroundImages.length;

        if (background1.style.opacity === '1') {
            background2.style.backgroundImage = backgroundImages[currentBackground];
            background1.style.opacity = '0';
            background2.style.opacity = '1';
        } else {
            background1.style.backgroundImage = backgroundImages[currentBackground];
            background2.style.opacity = '0';
            background1.style.opacity = '1';
        }
    }

    setInterval(changeBackground, 5000);
    changeBackground();
});