package pdp.hycode.pdp
        ;

/**
 * Created by HyCode on 12/22/2017.
 */


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ViewPagerAdapterState extends FragmentPagerAdapter {
    FragmentManager fragmentManager;
    SessionManagement sessionManagement;
    //    private String title[] = {"Location", "City","State","Router", "Business"};
    private String title[] = {"", "","",""};
    public ViewPagerAdapterState(FragmentManager manager, Context _context) {
        super(manager);
        fragmentManager=manager;
        sessionManagement=new SessionManagement(_context);
        title[0] ="Lagos State PDP ";// {"Location", "City","State"};
        title[1] ="Lagos State Images";//sessionManagement.get("City")+" City";
        title[2] ="Lagos State Videos";//sessionManagement.get("State")+" State";
        title[3]="Other News";

    }
    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new CityTabFragment();
            case 1:
                // City fragment activity
                return new ImagesTabfragment();
            case 2:
                return new ImagesTabfragment();
            case 3:
                return  new ImagesTabfragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }


}