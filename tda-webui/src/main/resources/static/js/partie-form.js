function initEstFaitToggle() {
    const toggle = document.getElementById('estFait');
    if (!toggle) {
        return;
    }

    const label = document.querySelector('label.form-check-label[for="estFait"]');
    const updateLabel = () => {
        if (label) {
            label.textContent = toggle.checked ? 'Fait' : 'Chuté';
        }
    };

    toggle.addEventListener('change', updateLabel);
    updateLabel();
}

document.addEventListener('DOMContentLoaded', initEstFaitToggle);
