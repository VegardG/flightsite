function fetchAircraftInfo(model) {
    fetch(`http://localhost:8081/aircraft/info/${model}`) // Adjust the URL as needed
        .then(response => response.json())
        .then(data => {
            displayAircraftInfo(data);
        })
        .catch(error => console.error('Error fetching aircraft info:', error));
}

function displayAircraftInfo(data) {
    const infoContainer = document.getElementById('aircraft-info');
    // Populate infoContainer with data about the aircraft
}

// Call this function periodically
setInterval(() => fetchAircraftInfo('B73'), 10000); // Adjust the interval as needed