
function fetchAircraftInfo(model) {
    console.log("Fetching aircraft information", model);

    fetch(`http://localhost:8082/aircraft/${model}`)
        .then(response => response.json())
        .then(data => {
            try {
                displayAircraftInfo(data);
            } catch (error) {
                console.error('Error in handling fetch success:', error);
            }
        })
        .catch(error => console.error('Error fetching aircraft data:', error))
        .finally(() => {
            setTimeout(() => fetchAircraftInfo(model), 10000);
    })
}

function displayAircraftInfo(data) {
    const infoContainer = document.getElementById('aircraft-info');
    infoContainer.innerHTML = `${data.info}`;
}

function getCurrentModel() {
    if (typeof filterModel !== 'undefined') {
        return filterModel;
    } else {
        throw new Error("Model not defined on this page.");
    }
}

fetchAircraftInfo(getCurrentModel());