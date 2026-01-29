let stompClient = null;
const ctx = document.getElementById('myChart');
let chartInstance = null;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        document.getElementById('status').innerText = '🟢';
        stompClient.subscribe('/topic/state', function (message) {
            const state = JSON.parse(message.body);
            updateState(state);
        });
        stompClient.send("/app/state.get", {}, {});
    }, function (error) {
        document.getElementById('status').innerText = '🔴 ' + error;
    });
}

function updateState(state) {
    if(state.level === 100) recuperationData();
}

function recuperationData(){
    axios.get('/api/public/etatReunion')
        .then(response => {
            const data = response.data;
            showData(data);
        })
        .catch(error => {
            console.error("Erreur lors de la récupération des informations :", error);
            document.getElementById('error').innerHTML = "<p class='text-danger'>Impossible de charger les informations.</p>";
        });
}

function showData(data) {
    let status = data.status;
    let resume = data.resume;
    let scores = data.scores;
    let labels = data.labels;
    let datasets = data.datasets;
    let logs = data.logs;
    document.getElementById('resume').innerHTML = data.resume;
    if (status > 1) {
        afficheScores(scores);
        afficheParties(data.parties);
        afficheGraph(labels, datasets);
        afficheLogs(logs);
        console.log(logs);
        return;
    }

    // Reset UI when reunion is not active (ex: RAZ)
    const scoresDiv = document.getElementById('scores');
    if (scoresDiv) {
        scoresDiv.innerHTML = "<p style='padding: 10px'>Aucun score disponible.</p>";
    }
    const manchesDiv = document.getElementById('manches');
    if (manchesDiv) {
        manchesDiv.innerHTML = "<p style='padding: 10px'>Aucune partie à afficher.</p>";
    }
    const logsDiv = document.getElementById('logs');
    if (logsDiv) {
        logsDiv.innerHTML = "<p style='padding: 10px'>Aucun log disponible.</p>";
    }
    if (chartInstance) {
        chartInstance.destroy();
        chartInstance = null;
    }
}

function afficheScores(scores) {
    const scoresDiv = document.getElementById('scores');
    if (!scores || scores.length === 0) {
        scoresDiv.innerHTML = "<p style='padding: 10px'>Aucun score disponible.</p>";
        return;
    }

    let table = scoresDiv.querySelector('table');
    if (!table) {
        table = document.createElement('table');
        table.className = 'table table-striped mt-3';
        table.appendChild(document.createElement('tbody'));
        scoresDiv.innerHTML = '';
        scoresDiv.appendChild(table);
    }

    const tbody = table.querySelector('tbody');
    const existingRows = new Map();
    tbody.querySelectorAll('tr[data-nom]').forEach(row => {
        existingRows.set(row.dataset.nom, row);
    });

    const fragment = document.createDocumentFragment();
    scores.forEach(joueur => {
        let row = existingRows.get(joueur.nom);
        if (!row) {
            row = document.createElement('tr');
            row.dataset.nom = joueur.nom;
            row.innerHTML = `
                <td style="font-weight: bold; font-size: 1.7em;">
                    <img alt="" width="40" height="40" style="margin-right: 8px; vertical-align: middle;">
                    <span class="score-name"></span>
                </td>
                <td class="text-end score-value" style="font-weight: bold; font-size: 2.3em;"></td>
            `;
        }

        const img = row.querySelector('img');
        if (img && img.getAttribute('src') !== joueur.avatar) {
            img.setAttribute('src', joueur.avatar);
        }
        const nameSpan = row.querySelector('.score-name');
        if (nameSpan) {
            nameSpan.textContent = joueur.nom;
        }
        const scoreCell = row.querySelector('.score-value');
        if (scoreCell) {
            scoreCell.textContent = joueur.score;
            scoreCell.style.color = joueur.color;
        }

        fragment.appendChild(row);
        existingRows.delete(joueur.nom);
    });

    existingRows.forEach(row => row.remove());
    tbody.appendChild(fragment);
}

function afficheParties(parties) {
    const manchesDiv = document.getElementById('manches');
    const isAuthenticated = Boolean(document.getElementById('auth-flag'));

    // Si l'onglet / fragment n'est pas présent dans le DOM, on évite l'erreur
    if (!manchesDiv) {
        console.warn("L'élément #manches n'a pas été trouvé dans la page.");
        return;
    }

    if (!Array.isArray(parties) || parties.length === 0) {
        manchesDiv.innerHTML = "<p style='padding: 10px'>Aucune partie à afficher.</p>";
        return;
    }

    // Une string par ligne => liste HTML
    manchesDiv.innerHTML = `
        <ul class="list-group mt-3">
            ${parties.map((p, index) => {
                const content = escapeHtml(p);
                if (!isAuthenticated) {
                    return `<li class="list-group-item">${content}</li>`;
                }
                const editUrl = `/admin/partie/${index + 1}`;
                return `
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <span>${content}</span>
                        <a class="btn btn-sm btn-outline-primary" href="${editUrl}">✏️ Modifier</a>
                    </li>
                `;
            }).join('')}
        </ul>
    `;

}

function escapeHtml(str) {
    if (str === null || str === undefined) return '';
    return String(str)
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;')
        .replaceAll("'", '&#39;');
}

function afficheGraph(labels, datasets) {
    if (chartInstance) {
        chartInstance.data.labels = labels;
        chartInstance.data.datasets = datasets;
        chartInstance.update();
        return;
    }

    chartInstance = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: datasets
        },
        options: {
            animations: {
                tension: {
                    duration: 1500,
                    easing: 'linear',
                    from: 0.5,
                    to: 0,
                    loop: false
                }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function afficheLogs(logs) {
    const logsDiv = document.getElementById('logs');
    if (!logsDiv) {
        console.warn("L'élément #logDiv n'a pas été trouvé dans la page.");
        return;
    }

    if (!logs || logs.length === 0) {
        logsDiv.innerHTML = "<p style='padding: 10px'>Aucun log disponible.</p>";
        return;
    }

    logsDiv.innerHTML = `
        <ul class="list-group mt-3">
            ${logs.map((log) => `<li class="list-group-item">${escapeHtml(log)}</li>`).join('')}
        </ul>
    `;
}

connect();
