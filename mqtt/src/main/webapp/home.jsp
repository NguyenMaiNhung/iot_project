<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Biểu đồ đường với thời gian thực</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
.container {
	margin-top: 20px;
}

.gauge {
	width: 100%;
	max-width: 250px;
	font-family: "Roboto", sans-serif;
	font-size: 32px;
	color: #004033;
}

.gauge__body {
	width: 100%;
	height: 0;
	padding-bottom: 50%;
	background: #b4c0be;
	position: relative;
	border-top-left-radius: 100% 200%;
	border-top-right-radius: 100% 200%;
	overflow: hidden;
}

.gauge-fill {
	position: absolute;
	top: 100%;
	left: 0;
	width: inherit;
	height: 100%;
	background: #009578;
	transform-origin: center top;
	transform: rotate(0.25turn);
	transition: transform 0.2s ease-out;
}

.gauge-cover {
	width: 75%;
	height: 150%;
	background: #ffffff;
	border-radius: 50%;
	position: absolute;
	top: 25%;
	left: 50%;
	transform: translateX(-50%);
	/* Text */
	display: flex;
	align-items: center;
	justify-content: center;
	padding-bottom: 25%;
	box-sizing: border-box;
}

.hidden {
	display: none;
	margin-top: 30px;
}

#myLineChart {
	width: 60%;
}

input {
	border-radius: 5%;
}

a.disabled {
	pointer-events: none;
	color: grey;
	text-decoration: none;
}
</style>
</head>
<body>
	<header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand" href="/index">${typeF }</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </nav>
    </header>
    <br><br>
	<div class="container">
		<div class="row" style="display: flex;">
			<div class="col-md-4">
				<div class="gauge">
					<div class="gauge__body">
						<div class="gauge-fill"></div>
						<div class="gauge-cover"></div>
					</div>
					<br>
					<div style="display: flex;">
						<button class="btn btn-primary"
							style="width: 50%; margin-right: 10px;" id="manual">Manual</button>
						<button class="btn btn-primary" style="width: 50%;" id="auto">Auto</button>

					</div>
				</div>
			</div>
			<div>
				Ngưỡng trên: <input type="number" value="${max }" name="max"
					disabled="disabled"><br> <br> Ngưỡng dưới: <input
					type="number" value="${min }" name="min" disabled="disabled">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br> <br>
				Status:<br>
				<textarea name="" id="statusPump" cols="40" rows="6"
					style="border-radius: 5%; background-color: #009578;" disabled>Nothing</textarea>
			</div>
		</div>
	</div>
	<br>
	<br>
	<div>
		<h2>Biểu đồ số liệu độ ẩm đất thời gian thực</h2>
		<div style="width: 60%;">
			<canvas id="myLineChart"></canvas>
		</div>
	</div>



	<script type="text/javascript">

        // Khởi tạo biểu đồ với dữ liệu ban đầu và label thời gian hiện tại
        var ctx = document.getElementById("myLineChart").getContext('2d');
        var myLineChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [],
                datasets: [{
                    label: "Dữ liệu độ ẩm đất thu được",
                    fill: false,
                    lineTension: 0.1,
                    backgroundColor: "rgba(75,192,192,0.4)",
                    borderColor: "rgba(75,192,192,1)",
                    data: [],
                }]
            },
        });
        
        function fetchDataAndUpdateChart() {
            $.ajax({
                url: '/home/time/hum',
                dataType: 'json',
                success: function (data) {
                	console.log('tmp');
                    updateChart(data.time, data.values);
                },
                error: function () {
                    console.error('Error fetching data from the server.');
                }
            });
        }
       
        function updateChart(currentTime, newData) {
            myLineChart.data.datasets[0].data = newData;
            myLineChart.data.labels.splice(0, myLineChart.data.labels.length);
            var times = currentTime;
            for (let i = 0; i < times.length; i++) {
            	myLineChart.data.labels.push(times[i]);
           	}

            // Cập nhật label thời gian
            const maxLabelsToShow = 10;
            if (myLineChart.data.labels.length > maxLabelsToShow) {
                //myLineChart.data.labels.shift();
            	myLineChart.data.labels.splice(10);
            }

            myLineChart.update();
            
        }
        // Cập nhật dữ liệu và label mỗi 1 giây
        setInterval(fetchDataAndUpdateChart, 1000);
        
    </script>


	<script type="text/javascript">
        const gaugeElement = document.querySelector(".gauge");
        function setGaugeValue(gauge, value) {
            if (value < 0 || value > 1) {
            	console.log(value);
                return;
            }
            let value_str = 'rotate(' + (value/2).toString(10) + 'turn)'; 
            gauge.querySelector(".gauge-fill").style.transform = value_str;
            var res1 = Math.ceil(value * 100);
            let res = res1.toString(10) + '%'; 
            gauge.querySelector(".gauge-cover").textContent = res;
        }

        function updateGause() {
         	// Gửi yêu cầu Ajax đến server
            $.ajax({
                url: '/update-gauge', 
                method: 'GET', 
                success: function(response) {
                    // Xử lý dữ liệu nhận được từ server (nếu cần)
                    var value = parseFloat(response) / 100; // Đảm bảo rằng giá trị nhận được là số
                    setGaugeValue(gaugeElement, value); // Cập nhật giá trị cho gauge
                },
                error: function(error) {
                    console.error('Error updating gauge:', error);
                }
            });
        }

        setInterval(updateGause, 1000);

        setTimeout(function () {
            // Hiển thị phần tử
            var temporaryInfo = document.getElementById('temporaryInfo');
            temporaryInfo.classList.remove('hidden');

            // Ẩn đi sau 2 giây
            setTimeout(function () {
                temporaryInfo.classList.add('hidden');
            }, 2000);
        }, 5000);
    </script>

	<script type="text/javascript">
	    var intervalId;
	    var isCalling = false;
	
	    $(document).ready(function() {
	        $('#auto').on('click', function() {
	            if (!isCalling) {
	                isCalling = true;
	                startInterval();
	            } else {
	                clearInterval(intervalId);
	                isCalling = false;
	            }
	        });
	    });
	
	    function startInterval() {
	        intervalId = setInterval(function() {
	            // Gọi API ở đây
	            callApi();
	        }, 1000); // Thời gian giữa các lần gọi, ở đây là 1 giây (1000 miliseconds)
	    }
	
	    function callApi() {
	        $.ajax({
	            url: '/auto/open',
	            type: 'GET',
	            success: function(response) {
	                console.log('API Response:', response);
	                document.getElementById('statusPump').textContent = 'Status Pump: ' + response;
	            },
	            error: function(xhr, status, error) {
	                console.error('Error:', error);
	            }
	        });
	    }

    </script>

	<script>
        var ajaxRequest;
        var apiUrls = [
            '/manual/open',
            '/manual/close'
        ];
        var currentApiIndex = 0;

        $(document).ready(function() {
            $('#manual').on('click', function() {
                if (ajaxRequest) {
                    ajaxRequest.abort(); // Hủy request AJAX nếu đã bắt đầu
                }
                callAlternateApi();
            });
        });

        function callAlternateApi() {
            var apiUrl = apiUrls[currentApiIndex];
            ajaxRequest = $.ajax({
                url: apiUrl,
                type: 'GET',
                success: function(response) {
                	console.log('API Response:', response);
	                document.getElementById('statusPump').textContent = 'Status Pump: ' + response;
                },
                error: function(xhr, status, error) {
                    console.error('Error:', error);
                }
            });
            currentApiIndex = (currentApiIndex + 1) % apiUrls.length; // Chuyển đổi giữa hai API
        }
    </script>

	<script>
        var buttonA = document.getElementById('manual');
        var buttonB = document.getElementById('auto');

        buttonA.addEventListener('click', function () {

            if (buttonA.classList.contains('clicked')) {
                buttonA.classList.remove('clicked');
                buttonB.disabled = false;
            }
            else {
                buttonA.classList.add('clicked');
                buttonB.disabled = true;
            }
        });

        buttonB.addEventListener('click', function () {

            if (buttonB.classList.contains('clicked')) {
                buttonB.classList.remove('clicked');
                buttonA.disabled = false;
            }
            else {
                buttonB.classList.add('clicked');
                buttonA.disabled = true;
            }
        });
    </script>

</body>

</html>