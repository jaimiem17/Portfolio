% Jaimie Morris

clear all
close all
%% Part 1 Generate pure sinusoids and check Fast Fourier Transform
Fs = 44100; %Sampling frequency
t = (0:(1/Fs):2); %time vector in seconds
f = 392; %Frequency in Hz
x = 0.5.*cos(2*pi*f*t); %pure cosine signal

figure(1)
plot(t,x)
axis([0 0.05 -1.5 1.5]);
title(['x(t)=cos(2\pi*' num2str(f) ')'],'FontSize',16);
xlabel('t','FontSize',16);
ylabel('x(t)','FontSize',16);

tones = audioplayer(x,Fs);
playblocking(tones)

fftdata = fft(x); %Take the Fast Fourier Transform of the signal
fftmag = abs(fftdata); %Compute the magnitude of the FFT
figure(2);
freqs = [0: ((Fs/2)-1)]./2;
plot(freqs, fftmag(1:Fs/2))
%axis([0 1200 0 16000]);
xlabel('Frequency, f (Hz)','FontSize',16)
ylabel('FFT Magnitude','FontSize',16)

f = 5; %Frequency in Hz
x = 0.5.*cos(2*pi*f*t); %pure cosine signal
tones = audioplayer(x,Fs);
playblocking(tones)

f = 10; %Frequency in Hz
x = 0.5.*cos(2*pi*f*t); %pure cosine signal
tones = audioplayer(x,Fs);
playblocking(tones)

f = 100; %Frequency in Hz
x = 0.5.*cos(2*pi*f*t); %pure cosine signal
tones = audioplayer(x,Fs);
playblocking(tones)

f = 400; %Frequency in Hz
x = 0.5.*cos(2*pi*f*t); %pure cosine signal
tones = audioplayer(x,Fs);
playblocking(tones)

f = 1000; %Frequency in Hz
x = 0.5.*cos(2*pi*f*t); %pure cosine signal
tones = audioplayer(x,Fs);
playblocking(tones)

fftdata = fft(message); %Take the Fast Fourier Transform of the signal
fftmag = abs(fftdata); %Compute the magnitude of the FFT

%% Part 2. Amplidtude modulation on a more complex signal

Fs = 44100; %sampling frequency
t = (0:(1/Fs):2); %time vector in seconds
f_x = 349.3;
f_y = 436.7;
f_z = 524;
x = 0.5.*cos(2*pi*f_x*t);
y = cos(2*pi*f_y*t + pi/3);
z = 0.5.*cos(2*pi*524*t + pi/6);
figure(3);
plot(t,x);
axis([0 0.05 -1.5 1.5]);

title(['x(t)=0.5*cos(2\pi*' num2str(f_x) ')'],'FontSize',16);
figure(4);
plot(t,y);
axis([0 0.05 -1.5 1.5]);
title(['y(t)=cos(2\pi*' num2str(f_y) '+ \pi/3)'],'FontSize',16);
figure(5);
plot(t,z);
axis([0 0.05 -1.5 1.5]);
title(['z(t)=0.5*cos(2\pi*' num2str(f_z) '+ \pi/6)'],'FontSize',16);

a = (0.5.*x + y + 0.5.*z)/3; %average the signals
figure(6);
plot(t, a);
axis([0 0.05 -1.5 1.5]);
title('a = x + y + z');

tones = audioplayer(a,Fs);
playblocking(tones)
fftdata = fft(a);
fftmag = abs(fftdata); %Take the magnitude of amplitude frequency spectrum
fftangle = angle(fftdata); %Get the angle (phase) frequency spectrum

figure(7);
freqs = [0: (Fs/2)-1]./2;
plot(freqs, fftmag(1:Fs/2))
axis([0 1200 0 16000]);
xlabel('Frequency (Hz)','FontSize',14)
ylabel('FFT Magnitude','FontSize',14)
title('Magnitude of FFT','FontSize',14)

figure(8);
freqs = [0: (Fs/2)-1]./2;
plot(freqs, fftangle(1:Fs/2))
xlabel('Frequency (Hz)','FontSize',14)
ylabel('FFT Angle (phase)','FontSize',14)
title('Angle of FFT','FontSize',14)