document.addEventListener('DOMContentLoaded', function () {
    const map = L.map('map').setView([51.505, -0.09], 13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    fetchFlightDataAndDisplay(map);
});

function fetchFlightDataAndDisplay(map, aircraftModel = '') {
    fetch('http://localhost:8081/flights/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(function (dataPoint) {
                    if (dataPoint.lat && dataPoint.lng) {
                        L.marker([dataPoint.lat, dataPoint.lng])
                            .addTo(map)
                            .bindPopup(
                                'Flight: ' + (dataPoint.flight_icao || 'Unknown') +
                                '<br>Aircraft: ' + (dataPoint.aircraft_icao || 'N/A') +
                                '<br>Altitude: ' + (dataPoint.alt || 'N/A') +
                                '<br>Speed: ' + (dataPoint.speed || 'N/A')
                            );
                    }
                });
        })
        .catch(error => console.error('Could not fetch data: ', error));
}


function clearMarkers(map){
    map.eachLayer(function (layer) {
        if (!!layer.toGeoJSON) {
            map.removeLayer(layer);
        }
    });
}

/*map.on('moveend', function() {
    var bounds = map.getBounds();
    fetchFlightsInRange(map, bounds);
});

function fetchFlightsInRange(map, bounds) {
    var url = `http://localhost:8081/flights/bounds?minLat=${bounds.getSouth()}&maxLat=${bounds.getNorth()}&minLng=${bounds.getWest()}&maxLng=${bounds.getEast()}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            //
        })
        .catch(error => console.error('Could not get data: ', error))
}*/