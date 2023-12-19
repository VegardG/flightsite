// Initializes the map at Oslo
const map = L.map('map').setView([59.9, 10.7], 9);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

// Event listener that fetches and displays flights on the map after DOM content is loaded
document.addEventListener('DOMContentLoaded', function () {
    fetchFlightDataAndDisplay(map);
});

// Function to fetch the flights annd display the markers on the map
function fetchFlightDataAndDisplay(map, filterModel = '') {
    fetch('http://localhost:8081/flights/all')
        .then(response => response.json())
        .then(data => {
            clearMarkers(map);
            data.forEach(function (dataPoint) {
                if (shouldDisplayAircraft(dataPoint, filterModel)) {
                    const planeIcon = createRotatedIcon(dataPoint.dir);
                    L.marker([dataPoint.lat, dataPoint.lng], { icon: planeIcon })
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

// Decides to display a flight based on the filter
function shouldDisplayAircraft(dataPoint, filterModel) {
    return filterModel === '' || (dataPoint.aircraft_icao && dataPoint.aircraft_icao.includes(filterModel));
}

// Listens for a change in the filter and updates the map markers if needed
document.addEventListener('DOMContentLoaded', function () {
    const aircraftFilter = document.getElementById('aircraftFilter');

    aircraftFilter.addEventListener('change', function () {
        const selectedAircraft = this.value;
        clearMarkers(map);
        fetchFlightDataAndDisplay(map, selectedAircraft);
    });
});

// Clears all markers on the map
function clearMarkers(map) {
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker) {
            map.removeLayer(layer);
        }
    });
}

// Custom icon for the flights on the map and rotates based on the heading
function createRotatedIcon(heading) {
    const iconHtml = '<div style="transform: rotate(' + heading + 'deg); width: 20px; height: 20px; display: flex; justify-content: center; align-items: center;">' +
        '<img src="../aircraftimages/airplanelogo.png" style="width: 100%; height: auto;"/>' +
        '</div>';

    return L.divIcon({
        html: iconHtml,
        iconSize: [20, 20],
        iconAnchor: [10, 10],
        className: ''
    });
}