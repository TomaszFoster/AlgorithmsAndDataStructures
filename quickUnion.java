// Quick Union (lazy implementation)

public class QuickUnionUF
{
    private int[] id;

    // Set id of each object to itself (N array accesses)
    public QuickUnionUF(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    // chase parent points until reach root (depth of i array accesses)
    private int root(int i)
    {
        while (i != id[i]) i = id[i];
        return i;
    }

    // check if p and q have same root (depth of p and q array acceses)
    public boolean connected(int p, int q)
    {
        return root(p) == root(q);
    }

    // change root of p to point to root of q (depth of p and q array accesses)
    public void union(int p, int q)
    {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }
}