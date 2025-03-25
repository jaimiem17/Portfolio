% Jaimie Morris
% Lab 2
% Due 3/11/2025

close all;
clear all;

%% 1. Write a function that calculates the Fourier series of square wave and sawtooth wave

fs = 28000; % Sampling frequency
f = 440;    % Fundamental frequency in Hz
t = [0:(1/fs):1]; % Time vector (1 second)

% Square wave calculation
w = [1:2:41]; % Odd frequencies for square wave Fourier series
i = [1:1:21]; % Counter
a_n = ((-1).^(i+1)).*(1./w); % Amplitudes for square wave
x_square = zeros(22, length(t));
x_square(1,:) = (1/2).*ones(1,length(t)); % DC term

for n=1:1:21
    x_square(n+1,:) = 2/pi.*(a_n(n).*cos(w(n)*2*pi*f*t)); % Compute non dc term
end

% Sawtooth wave calculation
x_sawtooth = zeros(21, length(t));
% No DC term for sawtooth wave as given

for n=1:1:20
    x_sawtooth(n+1,:) = (2/pi) * (1/n) * cos(-n*pi*f*t+( (-1.^n).*(pi/2)));
end

%% 2. Plot the sum of the Fourier series up to the 20th harmonic
% Square wave plot
figure
plot(t, sum(x_square(1:21,:),1))
xlabel('Time (s)','FontSize',14)
ylabel('x(t)','FontSize',14)
title('Square Wave - Sum to 20^t^h Harmonic','FontSize',18)
xlim([0 0.01]) % Zooming in to see one period

% Sawtooth wave plot
figure
plot(t, sum(x_sawtooth(1:21,:),1))
xlabel('Time (s)','FontSize',14)
ylabel('x(t)','FontSize',14)
title('Sawtooth Wave - Sum to 20^t^h Harmonic','FontSize',18)
xlim([0 0.01]) % Zooming in to see one period

%% 3. Listen to the harmonics at different levels

% Square wave 
disp('Playing square wave with 1st harmonic')
sum_square_1 = audioplayer(sum(x_square(1:2,:),1), fs);
playblocking(sum_square_1);

disp('Playing square wave with 5th harmonic')
sum_square_5 = audioplayer(sum(x_square(1:6,:),1), fs);
playblocking(sum_square_5);

disp('Playing square wave with 20th harmonic')
sum_square_20 = audioplayer(sum(x_square(1:21,:),1), fs);
playblocking(sum_square_20);

% Sawtooth wave
disp('Playing sawtooth wave with 1st harmonic')
sum_sawtooth_1 = audioplayer(sum(x_sawtooth(1:2,:),1), fs);
playblocking(sum_sawtooth_1);

disp('Playing sawtooth wave with 5th harmonic')
sum_sawtooth_5 = audioplayer(sum(x_sawtooth(1:6,:),1), fs);
playblocking(sum_sawtooth_5);

disp('Playing sawtooth wave with 20th harmonic')
sum_sawtooth_20 = audioplayer(sum(x_sawtooth(1:21,:),1), fs);
playblocking(sum_sawtooth_20);


% As more harmonics are added, the square wave sound becomes more buzzy and harsh
% The difference is due to the harmonic content - square waves have only odd
% harmonics while sawtooth waves have both odd and even harmonics.

%% 4. Sound signal B(t) scaled with a decaying exponential
% Create sequence of notes with sawtooth wave
freq_sequence = [278 278 417 417 468 468 417];
half_second = fs/2; % Number of samples for half a second

% Initialize empty array for the complete signal
complete_signal = zeros(1, half_second * length(freq_sequence));

% Create sawtooth approximation to 40 harmonics
for note_idx = 1:length(freq_sequence)
    current_f = freq_sequence(note_idx);
    t_note = [0:(1/fs):0.5-1/fs]; % Half second time vector

    % Initialize the signal for this note
    note_signal = zeros(1, length(t_note));

    % Generate sawtooth with 40 harmonics
    for n = 1:40
        if mod(n, 2) == 1 % Odd harmonics
            harmonic = (2/pi) * (1/n) * cos(n*2*pi*current_f*t_note - pi/2);
        else % Even harmonics
            harmonic = (2/pi) * (1/n) * cos(n*2*pi*current_f*t_note + pi/2);
        end
        note_signal = note_signal + harmonic;
    end

    % Apply exponential decay
    note_signal = exp(-2*t_note) .* note_signal;

    % Add to the complete signal
    start_idx = (note_idx-1) * half_second + 1;
    end_idx = note_idx * half_second;
    complete_signal(start_idx:end_idx) = note_signal;
end

% Play the complete signal
disp('Playing decaying sawtooth sequence')
player = audioplayer(complete_signal, fs);
playblocking(player);

%% 5. Repeat with pure sinusoidal signal
% Initialize empty array for the complete signal
sine_signal = zeros(1, half_second * length(freq_sequence));

% Create sinusoidal signal
for note_idx = 1:length(freq_sequence)
    current_f = freq_sequence(note_idx);
    t_note = [0:(1/fs):0.5-1/fs]; % Half second time vector

    % Generate pure sine wave
    note_signal = sin(2*pi*current_f*t_note);

    % Apply exponential decay
    note_signal = exp(-2*t_note) .* note_signal;

    % Add to the complete signal
    start_idx = (note_idx-1) * half_second + 1;
    end_idx = note_idx * half_second;
    sine_signal(start_idx:end_idx) = note_signal;
end

% Play the complete signal
disp('Playing decaying sine sequence')
player = audioplayer(sine_signal, fs);
playblocking(player);

% Explanation of the difference between sawtooth and sine:
% The sawtooth wave contains multiple harmonics which gives it a more buzzy sound
% The pure sine wave contains only the fundamental frequency,
% resulting in a purer, simpler sound. The difference is similar to the difference
% between different musical instruments playing the same note - the harmonics cause different intensities.



