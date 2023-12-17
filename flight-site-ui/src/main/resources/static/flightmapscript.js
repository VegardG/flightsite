const map = L.map('map').setView([51.505, -0.09], 13);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

document.addEventListener('DOMContentLoaded', function () {
    fetchFlightDataAndDisplay(map);
});

function fetchFlightDataAndDisplay(map, filterModel = '') {
    fetch('http://api-service:8080/flights/all')
        .then(response => response.json())
        .then(data => {
            clearMarkers(map);
            data.forEach(function (dataPoint) {
                if (shouldDisplayAircraft(dataPoint, filterModel)) {
                    L.marker([dataPoint.lat, dataPoint.lng])
                        .addTo(map)
                        .bindPopup(
                            'Flight: ' + (dataPoint.flight_icao || 'Unknown') +
                            '<br>Aircraft: ' + (dataPoint.aircraft_icao || 'N/A') +
                            '<br>Altitude: ' + (dataPoint.alt + ' m' || 'N/A') +
                            '<br>Speed: ' + (dataPoint.speed + ' km/h' || 'N/A')
                        );
                }
            });
        })
        .catch(error => console.error('Could not fetch data: ', error));
}

function shouldDisplayAircraft(dataPoint, filterModel) {
    return filterModel === '' || (dataPoint.aircraft_icao && dataPoint.aircraft_icao.includes(filterModel));
}

document.addEventListener('DOMContentLoaded', function () {
    const aircraftFilter = document.getElementById('aircraftFilter');

    aircraftFilter.addEventListener('change', function () {
        const selectedAircraft = this.value;
        clearMarkers(map);
        fetchFlightDataAndDisplay(map, selectedAircraft);
    });
});

function clearMarkers(map) {
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            map.removeLayer(layer);
        }
    });
}

