import java.util.Arrays;

public class HungarianAlgorithm {
    public static int[][] findAssignments(double[][] costMatrix) {
        int n = costMatrix.length;
        double[][] m = new double[n][n];
        double[] u = new double[n];
        double[] v = new double[n];
        int[] p = new int[n];
        int[] way = new int[n];

        for (int i = 0; i < n; i++) {
            u[i] = costMatrix[i][0];
            for (int j = 1; j < n; j++) {
                u[i] = Math.min(u[i], costMatrix[i][j]);
            }
        }

        for (int j = 0; j < n; j++) {
            v[j] = costMatrix[0][j] - u[0];
            for (int i = 1; i < n; i++) {
                v[j] = Math.min(v[j], costMatrix[i][j] - u[i]);
            }
        }

        Arrays.fill(p, -1);
        Arrays.fill(way, -1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = costMatrix[i][j] - u[i] - v[j];
            }
        }

        for (int i = 0; i < n; i++) {
            int j0 = -1;
            for (int j = 0; j < n; j++) {
                if (p[j] == -1 && (j0 == -1 || m[i][j] < m[i][j0])) {
                    j0 = j;
                }
            }
            p[j0] = i;

            int i0 = -1;
            for (int k = 0; k < n; k++) {
                if (way[k] == -1 || m[i][j0] < m[i0][j0]) {
                    i0 = k;
                }
            }
            way[i0] = j0;

            for (int k = 0; k < n; k++) {
                if (p[k] == -1) {
                    double delta = m[i][k] - m[p[j0]][k];
                    if (delta < m[i0][k]) {
                        i0 = k;
                    }
                    m[i][k] -= delta;
                    m[p[j0]][k] += delta;
                }
            }
        }

        int[][] assignments = new int[n][2];
        for (int j = 0; j < n; j++) {
            if (p[j] != -1) {
                assignments[j][0] = p[j];
                assignments[j][1] = j;
            }
        }

        return assignments;
    }
}