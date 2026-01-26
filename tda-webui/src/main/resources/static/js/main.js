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
    document.getElementById('resume').innerHTML = data.resume;
    if (status > 1) {
        afficheScores(scores);
        afficheParties(data.parties);
        afficheGraph(labels, datasets);
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

    let html = `
        <table class="table table-striped mt-3">
            <tbody>
    `;

    scores.forEach(joueur => {
        // On applique la couleur au score
        html += `
            <tr>
                <td style="font-weight: bold; font-size: 1.8em;">${joueur.nom}</td>
                <td class="text-end" style="color: ${joueur.color}; font-weight: bold; font-size: 2.5em;">${joueur.score}</td>
            </tr>
        `;
    });

    html += `
            </tbody>
        </table>
    `;

    scoresDiv.innerHTML = html;
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

connect();
