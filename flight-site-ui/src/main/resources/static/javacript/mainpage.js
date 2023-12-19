document.addEventListener('DOMContentLoaded', function() {
    const backgroundImages = [
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

    // Preload images for nice transition
    backgroundImages.forEach(function(img) {
        const image = new Image();
        image.src = img.slice(4, -1);
    });

    // Function to change background image
    function changeBackground() {
        currentBackground = (currentBackground + 1) % backgroundImages.length;

        // Alternate between two background elements for fade effect
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

    // Change background every 5 sec and set background instantly on load
    setInterval(changeBackground, 5000);
    changeBackground();
});