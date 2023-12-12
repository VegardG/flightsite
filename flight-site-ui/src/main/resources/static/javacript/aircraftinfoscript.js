function fetchAircraftInfo(model) {
    fetch('http://<service-url>/api/aircraft/737')
        .then(response => response.json())
        .then(data => {
            document.getElementById('aircraft-info').innerHTML = `Model: ${data.model}, Capacity: ${data.capacity}`;
        })
        .catch(error => console.error('Error fetching aircraft data:', error));
}

function displayAircraftInfo(data) {
    const infoContainer = document.getElementById('aircraft-info');
    // Populate infoContainer with data about the aircraft
}

// Call this function periodically
setInterval(() => fetchAircraftInfo('B73'), 10000); // Adjust the interval as needed