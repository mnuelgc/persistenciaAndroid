// Generated by view binder compiler. Do not edit!
package es.ua.eps.exercice2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import es.ua.eps.exercice2.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class NavHeaderSettingsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout navHeader;

  @NonNull
  public final ImageView navHeaderImageView;

  @NonNull
  public final TextView navHeaderTextView;

  private NavHeaderSettingsBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout navHeader,
      @NonNull ImageView navHeaderImageView, @NonNull TextView navHeaderTextView) {
    this.rootView = rootView;
    this.navHeader = navHeader;
    this.navHeaderImageView = navHeaderImageView;
    this.navHeaderTextView = navHeaderTextView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static NavHeaderSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NavHeaderSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.nav_header_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NavHeaderSettingsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout navHeader = (LinearLayout) rootView;

      id = R.id.nav_header_imageView;
      ImageView navHeaderImageView = ViewBindings.findChildViewById(rootView, id);
      if (navHeaderImageView == null) {
        break missingId;
      }

      id = R.id.nav_header_textView;
      TextView navHeaderTextView = ViewBindings.findChildViewById(rootView, id);
      if (navHeaderTextView == null) {
        break missingId;
      }

      return new NavHeaderSettingsBinding((LinearLayout) rootView, navHeader, navHeaderImageView,
          navHeaderTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
