package com.hajland.logic;

import com.mobeelizer.mobile.android.api.MobeelizerLoginStatus;

public interface LoginCallback {

	void onLoginFinished(final MobeelizerLoginStatus status);
}
