package com.mylowcarbon.app;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import com.mylowcarbon.app.browser.JsActionBarActivity;

/**
 * Created by Orange on 18-4-3.
 * Email:addskya@163.com
 */

public abstract class FragmentTransferActivity
        extends JsActionBarActivity
        implements FragmentTransfer {

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setupHomeFragment();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() >= 2) {
            manager.popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupHomeFragment() {
        String homeFragmentTag = getHomeFragmentTag();
        if (TextUtils.isEmpty(homeFragmentTag)) {
            throw new IllegalArgumentException("The homeFragment tag can't bu Empty");
        }
        Fragment homeFragment = createFragment(homeFragmentTag, null);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .addToBackStack(homeFragmentTag)
                .add(getContainerViewId(), homeFragment, homeFragmentTag)
                .commit();
    }

    /**
     * Optional identifier of the container this fragment is
     * to be placed in.  If 0, it will not be placed in a container.
     *
     * @return Fragment container id
     */
    @IdRes
    protected abstract int getContainerViewId();

    /**
     * Optional tag name for the fragment, to later retrieve the
     * fragment with {@link FragmentManager#findFragmentByTag(String)
     * FragmentManager.findFragmentByTag(String)}.
     *
     * @return the HomeFragment tag
     */
    @NonNull
    protected abstract String getHomeFragmentTag();

    /**
     * Create the Child Fragment by specified tag
     *
     * @param tag  Optional tag name for the fragment
     * @param args the launch args
     * @return the child fragment
     */
    protected abstract Fragment createFragment(@NonNull String tag,
                                               @Nullable Bundle args);

    @Override
    public void onTranslateTo(@NonNull String tag,
                              @Nullable Bundle args) {
        final FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            manager.popBackStackImmediate(tag, 0);
            return;
        }

        fragment = createFragment(tag, args);
        manager.beginTransaction()
                .setCustomAnimations(
                        R.anim.fragment_open_in,
                        R.anim.fragment_open_out,
                        R.anim.fragment_close_in,
                        R.anim.fragment_close_out)
                .replace(R.id.content, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void onFinish() {
        finish();
    }
}
