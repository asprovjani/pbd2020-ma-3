package si.uni_lj.fri.pbd.miniapp3.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import si.uni_lj.fri.pbd.miniapp3.ui.favorites.FavoritesFragment;
import si.uni_lj.fri.pbd.miniapp3.ui.search.SearchFragment;

public class SectionsPagerAdapter extends FragmentStateAdapter {
    private int tabCount;

    public SectionsPagerAdapter(FragmentActivity fragmentActivity, int tabCount) {
        super(fragmentActivity);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment f = null;
        switch(position) {
            case 0:
                f = new SearchFragment();
                break;
            case 1:
                f = new FavoritesFragment();
                break;
        }

        return f;
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
