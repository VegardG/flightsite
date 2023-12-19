
function fetchAircraftInfo(model) {
    console.log("Fetching aircraft information", model);

    // API request to get data for a certain model
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

// Displays aircraft information in the UI
function displayAircraftInfo(data) {
    const infoContainer = document.getElementById('aircraft-info');
    infoContainer.innerHTML = `${data.info}`;
}

// Gets the current model for the fetch request
function getCurrentModel() {
    if (typeof filterModel !== 'undefined') {
        return filterModel;
    } else {
        throw new Error("Model not defined on this page.");
    }
}

fetchAircraftInfo(getCurrentModel());