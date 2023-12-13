function fetchAircraftInfo(model) {
    fetch(`http://localhost:8082/aircraft/${model}`)
        .then(response => response.json())
        .then(data => {
            displayAircraftInfo(data);
        })
        .catch(error => console.error('Error fetching aircraft data:', error));
}

function displayAircraftInfo(data) {
    const infoContainer = document.getElementById('aircraft-info');
    infoContainer.innerHTML = `Info: ${data.info}`;
}

function getCurrentModel() {
    if (typeof filterModel !== 'undefined') {
        return filterModel;
    } else {
        throw new Error("Model not defined on this page.");
    }
}
try {
    setInterval(() => fetchAircraftInfo(getCurrentModel()), 10000);
} catch (error) {
    console.error(error.message);
}