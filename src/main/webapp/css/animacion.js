var canvas = document.getElementById('canvas');
var ctx = canvas.getContext('2d');
var img = new Image();
img.src = "images/LogoLetras.png";


'floor|ceil|random|round|abs|sqrt|PI|atan2|sin|cos|pow|max|min'
  .split('|')
  .forEach(function(p) { window[p] = Math[p]; });

var TAU = PI*2;

function r(n) { return random()*n; }
function rrng(lo, hi) { return lo + r(hi-lo); }
function rint(lo, hi) { return lo + floor(r(hi - lo + 1)); }
function choose(args) { return args[rint(0, args.length-1)]; }

/*---------------------------------------------------------------------------*/

var W, H, frame, t0, time;
var DPR = devicePixelRatio;

function dpr(n) {
  return n * DPR;
}

function resize() {
  var w = innerWidth;
  var h = innerHeight;

  canvas.style.width = w+'px';
  canvas.style.height = h+'px';

  W = canvas.width = w * DPR;
  H = canvas.height = h * DPR;
}

function loop(t) {
  frame = requestAnimationFrame(loop);
  draw();
  time++;
}

function pause() {
  cancelAnimationFrame(frame);
  frame = frame ? null : requestAnimationFrame(loop);
}

function reset() {
  cancelAnimationFrame(frame);
  resize();
  ctx.clearRect(0, 0, W, H);
  init();
  time = 0;
  frame = requestAnimationFrame(loop);
}

/*---------------------------------------------------------------------------*/

function Neuron(i, x, y, n) {
  this.i = i;
  this.x = x;
  this.y = y;
  this.n = n;
  this.indices = [];
}

Neuron.prototype.distanceTo = function(other) {
  var dx = this.x - other.x;
  var dy = this.y - other.y;
  return sqrt(dx*dx + dy*dy);
};

Neuron.prototype.setWeights = function(neurons) {
  var d, w, i;

  for (i = 0; i < neurons.length; i++) {
    d = this.distanceTo(neurons[i]);
    w = (d < STEP && d > 0.1) ? ceil(STEP/d) : 0;
    while(w--) this.indices.push(i);
  }
};

Neuron.prototype.draw = function() {
  if (this.n <= 0) return;
  ctx.beginPath();
  ctx.arc(this.x, this.y, dpr(this.n)/3, 0, TAU);
  ctx.fill();
};

Neuron.prototype.connect = function() {
  if (this.n <= 0 || this.indices.length === 0) return;

  var i;
  do { i = choose(this.indices); } while (this.i === i);
  var other = Neurons[i];

  ctx.moveTo(this.x, this.y);
  ctx.lineTo(other.x, other.y);

  this.n--;
  other.n++;
};

/*---------------------------------------------------------------------------*/
var Neurons;
var STEP;
var NN = 3000;
function clear() {
    ctx.drawImage(img, 400, 100,500,500);
  ctx.fillStyle = 'rgba(  30, 26, 32,1)';
  ctx.fillRect(0, 0, W, H);
}

function init() {
    ctx.drawImage(img, 400, 100,500,500);
  var i, j;
  Neurons = new Array(NN);
  STEP = min(W, H) / 20;

  for (i = 0; i < NN; i++) {
    Neurons[i] = new Neuron(i, r(W), r(H), 1);
  }

  for (i = 0; i < NN; i++) {
    n = Neurons[i];
    n.setWeights(Neurons);
  }

  clear();
}

function draw() {
  var i;
var c = 'rgba( 214, 130, 249,.1)';
var a = 'rgba( 249, 130, 246,.1)';
var b = 'rgba( 192, 130, 249,.1)';
 var grd = ctx.createLinearGradient(0, 0, 300, 0);
    grd.addColorStop(0, c);
    grd.addColorStop(0.4, a);
    grd.addColorStop(.6, b);

  if (time % 5 === 0) {
  ctx.drawImage(img, 400, 100,500,500);
      
    
    ctx.fillStyle = grd;
    ctx.strokeStyle = 'rgba( 179, 0, 255,0.1)';

    for (i = 0; i < NN; i++) { Neurons[i].draw(); }

    ctx.beginPath();
    for (i = 0; i < NN; i++) { Neurons[i].connect(); }
    ctx.stroke();
    
  }
  if (time % 300 === 0) { 
     ctx.drawImage(img, 400, 100,500,500);
      pause();
      reset();
     }
}

/*---------------------------------------------------------------------------*/

document.onclick = pause;
document.ondblclick = reset;
reset();


