// Quick Union (weighted) with path compression

public class QuickUnionUF
{
    private int[] id;
    private int[] size;
    private int count;

    // Set id of each object to itself (N array accesses)
    public QuickUnionUF(int N)
    {
        count = n;
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++)
        {
            id[i] = i;
            size[i] = 1;
        }
    }

    // validate that i is a valid index
    private void validate(int i)
    {
        int n = id.length;
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("index " + i + " is not between 0 and " + (n-1));  
        }
    }

    // chase parent points until reach root (depth of i array accesses)
    private int root(int i)
    {
        validate(i);
        while (i != id[i]){
            id[i] = id[id[i]]; // PART 2: only one extra line of code!
            i = id[i];
        }
        return i;
    }

    public int count()
    {
        return count;
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
        if(i == j) return;
        if (size[i] < size[j] )
        {
            id[i] = j;
            size[j] += size[i];
        } else {
            id[j] = i;
            size[i] += size[j];
        }
    }
}