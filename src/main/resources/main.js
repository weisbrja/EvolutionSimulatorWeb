let socket = new WebSocket('ws://localhost:8080/jetty'), canvas, button, context, currentData;

function onLoad() {
	canvas = document.getElementById('canvas');
	button = document.getElementById('button');
	canvas.width = window.innerWidth;
	canvas.height = window.innerHeight;
	context = canvas.getContext('2d');

	socket.onmessage = event => {
		currentData = JSON.parse(event.data);
		draw(currentData);
	};
}

function onResize() {
	canvas.width = window.innerWidth;
	canvas.height = window.innerHeight;
	draw(currentData);
}

function onClick() {
	socket.send('t');
	if (button.innerText === 'Turn graphical off')
		button.innerText = 'Turn graphical on';
	else
		button.innerText = 'Turn graphical off';
}

function draw(data) {
	drawBackground(data.v);

	for (let i = 0; i < data.m.length; ++i) {
		drawMuscle(data.m[i], data.v);
	}

	for (let i = 0; i < data.c.length; ++i) {
		drawCircle(data.c[i], data.v);
	}
}

function drawBackground(cameraPosition) {
	context.clearRect(0, 0, window.innerWidth, window.innerHeight);

	// draw the blue sky
	context.fillStyle = 'rgb(60, 180, 255)';
	context.fillRect(0, 0, window.innerWidth, window.innerHeight);

	context.fillStyle = 'rgb(50, 150, 200)';
	const width = 100;
	for (let i = -cameraPosition.x % (2 * width) - width; i <= window.innerWidth; i += 2 * width) {
		context.fillRect(i, 0, width, window.innerHeight);
	}

	// draw the red starting line
	context.strokeStyle = 'rgb(200, 0, 0)';
	context.lineWidth = 2;
	context.beginPath();
	context.moveTo(-cameraPosition.x, 0);
	context.lineTo(-cameraPosition.x, window.innerHeight);
	context.stroke();

	// draw the green ground
	context.fillStyle = 'rgb(30, 130, 0)';
	context.fillRect(0, -cameraPosition.y, window.innerWidth, window.innerHeight);
}

function drawCircle(circle, cameraPosition) {
	context.fillStyle = circle.f;
	context.beginPath();
	context.arc(circle.x - cameraPosition.x, circle.y - cameraPosition.y, circle.r, 0, 2 * Math.PI);
	context.fill();
}

function drawMuscle(muscle, cameraPosition) {
	context.strokeStyle = muscle.f;
	context.lineWidth = muscle.e ? 8 : 12;
	context.beginPath();
	context.moveTo(muscle.x0 - cameraPosition.x, muscle.y0 - cameraPosition.y);
	context.lineTo(muscle.x1 - cameraPosition.x, muscle.y1 - cameraPosition.y);
	context.stroke();
}
