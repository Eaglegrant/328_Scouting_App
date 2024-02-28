// Generated by view binder compiler. Do not edit!
package robotics.scouting.current.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import robotics.scouting.current.R;

public final class FragmentEndGameAllianceBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final EditText endPoints;

  @NonNull
  public final ExtendedFloatingActionButton fabFolder;

  @NonNull
  public final ExtendedFloatingActionButton fabGenerate;

  @NonNull
  public final ExtendedFloatingActionButton fabRead;

  @NonNull
  public final FrameLayout frameLayout;

  private FragmentEndGameAllianceBinding(@NonNull FrameLayout rootView, @NonNull EditText endPoints,
      @NonNull ExtendedFloatingActionButton fabFolder,
      @NonNull ExtendedFloatingActionButton fabGenerate,
      @NonNull ExtendedFloatingActionButton fabRead, @NonNull FrameLayout frameLayout) {
    this.rootView = rootView;
    this.endPoints = endPoints;
    this.fabFolder = fabFolder;
    this.fabGenerate = fabGenerate;
    this.fabRead = fabRead;
    this.frameLayout = frameLayout;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentEndGameAllianceBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentEndGameAllianceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_end_game_alliance, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentEndGameAllianceBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.endPoints;
      EditText endPoints = ViewBindings.findChildViewById(rootView, id);
      if (endPoints == null) {
        break missingId;
      }

      id = R.id.fabFolder;
      ExtendedFloatingActionButton fabFolder = ViewBindings.findChildViewById(rootView, id);
      if (fabFolder == null) {
        break missingId;
      }

      id = R.id.fabGenerate;
      ExtendedFloatingActionButton fabGenerate = ViewBindings.findChildViewById(rootView, id);
      if (fabGenerate == null) {
        break missingId;
      }

      id = R.id.fabRead;
      ExtendedFloatingActionButton fabRead = ViewBindings.findChildViewById(rootView, id);
      if (fabRead == null) {
        break missingId;
      }

      FrameLayout frameLayout = (FrameLayout) rootView;

      return new FragmentEndGameAllianceBinding((FrameLayout) rootView, endPoints, fabFolder,
          fabGenerate, fabRead, frameLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}