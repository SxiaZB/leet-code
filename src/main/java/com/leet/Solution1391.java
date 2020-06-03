package com.leet;

public class Solution1391 {
    /*
type DisjointSet struct {
pre []int
}

func NewDisjointSet(n int) *DisjointSet {
pre := make([]int, n)
for i := 0; i < n; i++ {
    pre[i] = i
}
return &DisjointSet{pre}
}

func (ds *DisjointSet) find(x int) int {
for ds.pre[x] != x {
    x = ds.pre[x]
}
return x
}

func (ds *DisjointSet) merge(x, y int) {
ds.pre[ds.find(x)] = ds.find(y)
}

func isAny(x int, nums ...int) bool {
for _, n := range nums {
    if x == n {
        return true
    }
}
return false
}

func idOf(x, y int) int {
return x * 300 + y
}


func tryLeft(ds *DisjointSet, grid [][]int, x, y int) {
    if y-1 >= 0 && isAny(grid[x][y-1], 1, 4, 6) {
            ds.merge(idOf(x, y), idOf(x, y-1))
    }
}

func tryRight(ds *DisjointSet, grid [][]int, x, y int) {
    if y+1 < len(grid[0]) && isAny(grid[x][y+1], 1, 3, 5) {
            ds.merge(idOf(x, y), idOf(x, y+1))
    }
}

func tryUp(ds *DisjointSet, grid [][]int, x, y int) {
    if x-1 >= 0 && isAny(grid[x-1][y], 2, 3, 4) {
            ds.merge(idOf(x, y), idOf(x-1, y))
    }
}

func tryDown(ds *DisjointSet, grid [][]int, x, y int) {
    if x+1 < len(grid) && isAny(grid[x+1][y], 2, 5, 6) {
            ds.merge(idOf(x, y), idOf(x+1, y))
    }
}

func hasValidPath(grid [][]int) bool {
m := len(grid)
n := len(grid[0])
ds := NewDisjointSet(m * 300 + n + 1)

for x := 0; x < m; x++ {
    for y := 0; y < n; y++ {
        switch grid[x][y] {
        case 1:
            tryLeft(ds, grid, x, y)
            tryRight(ds, grid, x, y)
        case 2:
            tryUp(ds, grid, x, y)
            tryDown(ds, grid, x, y)
        case 3:
            tryLeft(ds, grid, x, y)
            tryDown(ds, grid, x, y)
        case 4:
            tryRight(ds, grid, x, y)
            tryDown(ds, grid, x, y)
        case 5:
            tryUp(ds, grid, x, y)
            tryLeft(ds, grid, x, y)
        case 6:
            tryUp(ds, grid, x, y)
            tryRight(ds, grid, x, y)
        default:
            panic("非法的grid值")
        }
    }
}

// 只要检查出发点和目标点是不是联通的即可
return ds.find(idOf(0, 0)) == ds.find(idOf(m - 1, n - 1))
}*/
    // 上右下左
    private int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    //0：上 1：右 2：下 3：左
    private int[][] streets = {
            {1, 3}, {0, 2},
            {2, 3}, {1, 2},
            {0, 3}, {0, 1}};
    private int[][] grid;
    private int m, n;

    public boolean hasValidPath(int[][] grid) {
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        int[] street = streets[grid[0][0] - 1];
        for (int st = 0; st < street.length; st++) {
            int direction = street[st];
            if (go(0, 0, direction)) {
                return true;
            }
        }
        return false;
    }

    private boolean go(int x, int y, int direction) {
        //走到终点🏁
        if (x == m - 1 && y == n - 1) {
            return true;
        }
        x = x + directions[direction][0];
        y = y + directions[direction][1];
        //越界或者曾经来过就失败
        if (x < 0 || y < 0 || x >= m || y >= n || (grid[x][y] == -1)) {
            return false;
        }
        //下一个街道
        int street = grid[x][y];
        //需要的入口
        int entrance = (direction + 2) % 4;
        //从新街道找到入口，得到新的方向
        if (entrance == streets[street - 1][0]) {
            direction = streets[street - 1][1];
        } else if (entrance == streets[street - 1][1]) {
            direction = streets[street - 1][0];
        } else {
            //新街道没有入口
            return false;
        }
        //走过这个点
        grid[x][y] = -1;
        return go(x, y, direction);
    }
}
