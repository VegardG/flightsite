
document.addEventListener('DOMContentLoaded', function () {
    fetchAndDisplayCurrentFlights(filterModel);
});

function fetchAndDisplayCurrentFlights(filterModel) {
    fetch('http://localhost:8081/flights/all')
        .then(response => response.json())
        .then(data=> {
            displayFlights(data, filterModel);
        })
        .catch(error => console.error('Could not get flights: ', error));
}

function displayFlights(data, filterModel) {
    const container = document.getElementById('flights-container');
    container.innerHTML = '';

    data.forEach(function (dataPoint) {
        if (shouldDisplayAircraft(dataPoint, filterModel)) {
            const flightInfo = document.createElement('div');
            flightInfo.className = 'flight-info';
            flightInfo.innerHTML =`
                <p>Flight Number: ${dataPoint.flight_icao || 'Unknown'}</p>
                <p>Aircraft: ${dataPoint.aircraft_icao || 'N/A'}</p>
                <p>Altitude: ${dataPoint.alt ? dataPoint.alt + ' m' : 'N/A'}</p>
                <p>Speed: ${dataPoint.speed ? dataPoint.speed + ' km/h' : 'N/A'}</p>
                <p>Departure airport: ${dataPoint.dep_icao || 'Unknown'}</p>
                <p>Arrival airport: ${dataPoint.arr_icao || 'Unknown'}</p>
                    `;
            container.appendChild((flightInfo))
        }
    });
}

function shouldDisplayAircraft(dataPoint, filterModel) {
    return dataPoint.aircraft_icao && dataPoint.aircraft_icao.includes(filterModel);
}
