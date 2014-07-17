package screenful.apps;

import com.primesense.nite.JointType;
import com.primesense.nite.Point3D;
import com.primesense.nite.SkeletonJoint;
import com.primesense.nite.UserData;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import screenful.basic.NiTETracker;
import static screenful.gestures.Poses.dorkyClick;
import screenful.gui.visualization.BonesVisualization;
import screenful.gui.visualization.HandsVisualization;

/**
 * Show tracked torso coordinates until Enter is pressed, show some art if hands
 * are brought together above the neck.
 */
public class TorsoPrinter {

    public static void main(String[] args) {
        NiTETracker tracker = new NiTETracker();
        // uncomment for visualization windows
        /*
         // Add visualizations
         BonesVisualization skeleton = new BonesVisualization(tracker, "Skeleton tracker window");
         HandsVisualization hands = new HandsVisualization(tracker, "Hand tracker window");
         // Show visualizations
         skeleton.show();
         hands.show();
         */

        System.out.println("*** Press ENTER to quit.");
        try {
            while (System.in.available() == 0) {
                List<UserData> users = tracker.getBones();
                if (users.size() > 0) {
                    for (UserData user : users) {
                        SkeletonJoint torso = user.getSkeleton().getJoint(JointType.TORSO);
                        Point3D pos = torso.getPosition();
                        if (torso.getPositionConfidence() != 0.0) {
                            System.out.printf("Torso #%d: (%6d %6d %6d)\n",
                                    user.getId(), Math.round((float) pos.getX()), Math.round((float) pos.getY()), Math.round((float) pos.getZ()));
                        }
                        // hands near each other and above neck
                        if (dorkyClick(user)) {
                            click();
                        }
                    }
                }
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            Logger.getLogger(TorsoPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void click() {
        System.out.println("       _________________\n"
                + "      |.---------------.|\n"
                + "      ||    _          ||\n"
                + "      ||   |_)| |\\ /   ||\n"
                + "      ||   |_)|_/ |    ||\n"
                + "      ||          ,    ||\n"
                + "      ||__________|`.__||\n"
                + " jgs  '-----------|_ r--'\n"
                + "                    \\\n"
                + "                     `\n");
    }
}
