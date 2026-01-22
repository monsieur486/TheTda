let stompClient = null;
const ctx = document.getElementById('myChart');

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
    if(status > 1){
        afficheScores(scores);
        afficheParties(data.parties);
        afficheGraph(labels, datasets);
    }
}

function afficheScores(scores) {
    const scoresDiv = document.getElementById('scores');
    if (!scores || scores.length === 0) {
        scoresDiv.innerHTML = "<p>Aucun score disponible.</p>";
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
                <td style="font-weight: bold;">${joueur.nom}</td>
                <td class="text-end" style="color: ${joueur.color}; font-weight: bold;">${joueur.score}</td>
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

    // Si l'onglet / fragment n'est pas présent dans le DOM, on évite l'erreur
    if (!manchesDiv) {
        console.warn("L'élément #manches n'a pas été trouvé dans la page.");
        return;
    }

    if (!Array.isArray(parties) || parties.length === 0) {
        manchesDiv.innerHTML = "<p>Aucune partie à afficher.</p>";
        return;
    }

    // Une string par ligne => liste HTML
    manchesDiv.innerHTML = `
        <ul class="list-group mt-3">
            ${parties.map(p => `<li class="list-group-item">${escapeHtml(p)}</li>`).join('')}
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
    new Chart(ctx, {
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