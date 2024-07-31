let currentIndex = 0;

function navigate(direction) {
    const cardsWrapper = document.querySelector('.cards_wrapper');
    const cards = document.querySelectorAll('.card');
    const cardWidth = cards[0].offsetWidth + 55;    
    const visibleCards = Math.floor(cardsWrapper.offsetWidth / cardWidth);
    const maxIndex = cards.length - visibleCards;

    currentIndex += direction;

    if (currentIndex < 0) {
        currentIndex = 0;
    } else if (currentIndex > maxIndex) {
        currentIndex = maxIndex;
    }

    cardsWrapper.style.transform = `translateX(-${currentIndex * cardWidth}px)`;
}
