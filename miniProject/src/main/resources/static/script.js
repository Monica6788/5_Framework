document.addEventListener("DOMContentLoaded", () => {
    const badges = document.querySelectorAll(".badge");

    badges.forEach(badge => {
        const planId = badge.getAttribute('data-planid');
        const isImportant = localStorage.getItem(`badge_${planId}`);
        if (isImportant === 'true') {
            badge.classList.add('active');
        }

        badge.addEventListener('click', () => {
            badge.classList.toggle('active');
            const isActive = badge.classList.contains('active');
            localStorage.setItem(`badge_${planId}`, isActive);
        });
    });
});

