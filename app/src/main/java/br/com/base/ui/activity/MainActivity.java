package br.com.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import br.com.base.R;
import br.com.base.ui.fragment.CrudFragment;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.model.HelpLiveo;
import br.liveo.navigationliveo.NavigationActionBarLiveo;

public class MainActivity extends NavigationActionBarLiveo implements OnItemClickListener {

    @Override
    public void onInt(Bundle savedInstanceState) {

        HelpLiveo mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add("CRUD", R.drawable.ic_blur_circular_24dp, 7);

        with(this).startingPosition(0) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())
                .colorItemSelected(R.color.nliveo_blue_colorPrimary) //State the name of the color, icon and meter when it is selected
                .footerItem(R.string.settings, R.drawable.ic_settings_48dp)
                .setOnClickFooter(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, BackupActivity.class));
                        finish();
                    }
                })
                .removeHeader()
                .build();

        int position = this.getCurrentPosition();
        this.setElevationToolBar(position != 0 ? 15 : 0);
    }

    @Override
    public void onItemClick(int position) {

        Fragment mFragment;

        FragmentManager mFragmentManager = getSupportFragmentManager();

        switch (position) {

            case 0:
                setTitle("CRUD");
                mFragment = new CrudFragment();
                break;

            default:
                mFragment = new CrudFragment();
                break;
        }

        mFragmentManager.beginTransaction().replace(R.id.container, mFragment, String.valueOf(position)).commit();
        setElevationToolBar(position != 0 ? 15 : 0);
    }
}
