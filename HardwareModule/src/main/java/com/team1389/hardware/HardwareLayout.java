package com.team1389.hardware;

import com.team1389.hardware.watch.Watchable;

public interface HardwareLayout {
	public Watchable onPWM0(PWMPort port);
	public Watchable onPWM1(PWMPort port);
	public Watchable onPWM2(PWMPort port);
	public Watchable onPWM3(PWMPort port);
	public Watchable onPWM4(PWMPort port);
	public Watchable onPWM5(PWMPort port);
	public Watchable onPWM6(PWMPort port);
	public Watchable onPWM7(PWMPort port);
	public Watchable onPWM8(PWMPort port);
	public Watchable onPWM9(PWMPort port);
	public Watchable onPWM10(PWMPort port);
}
