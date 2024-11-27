var btn = document.getElementById("heartTxt");
btn.style.opacity = 0;
var btnVal = 0;

function showImage() {
    myImage.setAttribute("src", imageArray[imageIndex]);
    myTxt.innerHTML = txtArray[imageIndex];
}

function play() {
    if (t == 0) {
        // First click: hide type text and show images
        document.getElementById("typeDiv").style.opacity = 0;
        document.getElementById("imgTxt").style.opacity = 1;
        imageIndex = 0;
        showImage();
    } else {
        // Return to main page
        document.getElementById("typeDiv").style.opacity = 1;
        document.getElementById("imgTxt").style.opacity = 0;
        t = -1; // Reset t for next click
    }
    t++;
}

function buttonFadeIn() {
    if(btnVal < 1) {
        btnVal += 0.025;
        btn.style.opacity = btnVal;
    }
    else {
        clearInterval(buttonInterval);
        if(ok == 3) {
            ok += 1;
        }
    }
}

// Keyboard navigation
document.addEventListener('keydown', function(event) {
    if (document.getElementById("imgTxt").style.opacity == 1) {
        if (event.key === 'ArrowRight') {
            if (imageIndex < imageArray.length - 1) {
                imageIndex++;
                showImage();
            }
        } else if (event.key === 'ArrowLeft') {
            if (imageIndex > 0) {
                imageIndex--;
                showImage();
            }
        }
    }
});

function event() {
    document.getElementById("imgTxt").style.opacity = 0;
    setTimeout(function() {
        buttonInterval = setInterval(buttonFadeIn, 50);
    }, 1500);
}

var buttonInterval;
event();

// Initialize variables
var imageIndex = 0;
var t = 0;