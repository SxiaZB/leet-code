package com.leet;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution434T2 {
/*
        type Pos struct {
            x int
            y int
        }

        type DisjointSet struct {
            f map[Pos]Pos
        }

        func (ds *DisjointSet) find(pos Pos) Pos {
            for ds.f[pos] != pos {
                pre, ok := ds.f[pos]
                if !ok {
                    panic("data not in the disjoint set")
                }
                pos = pre
            }
            return pos
        }

        // 返回是不是真的发生了“merge”，如果原本就是联通的，就返回false
        func (ds *DisjointSet) merge(pos1, pos2 Pos) bool {
            root1, root2 := ds.find(pos1), ds.find(pos2)
            if root1 != root2 {
                ds.f[root1] = root2
                return true
            }
            return false
        }

        func numIslands2(m int, n int, positions [][]int) []int {
            ds := DisjointSet{
                f: make(map[Pos]Pos),
            }

            offsets := []Pos{Pos{0, 1}, Pos{1, 0}, Pos{0, -1}, Pos{-1, 0}}
            result := []int{}
            cur := 0 // 当前岛屿数量
            for _, p := range positions {
                pos := Pos{p[0], p[1]}

                // put the new position
                if _, ok := ds.f[pos]; ok {
                    //插入了同样的位置，忽略掉
                    result = append(result, cur)
                    continue
                }
                ds.f[pos] = pos
                cur++

                for _, o := range offsets {
                    neighbor := Pos{
                        x: pos.x + o.x,
                                y: pos.y + o.y,
                    }

                    if neighbor.x < 0 || neighbor.x >= m || neighbor.y < 0 || neighbor.y >= n {
                        continue
                    }

                    if _, ok := ds.f[neighbor]; ok {
                        // 如果邻居位置是岛屿，尝试合并
                        if merged := ds.merge(pos, neighbor); merged {
                            cur--
                        }
                    }
                }

                result = append(result, cur)
            }

            return result
        }
*/

    //(x,y)点的id=y*m+x,记录每个位置的root id
    private int[] islands;

    private int root(int island) {
        while (islands[island] != island) {
            islands[island] = islands[islands[island]];
            island = islands[island];
        }
        return island;
    }

    private int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public List<Integer> numIslands2(int m, int n, Point[] operators) {
        List<Integer> res = new ArrayList<>();
        if (operators == null || operators.length == 0) {
            return res;
        }

        islands = new int[m * n];
        Arrays.fill(islands, -1);
        int island = 0;
        for (int i = 0; i < operators.length; i++) {
            Point operator = operators[i];
            int id = operator.y * m + operator.x;
            if (islands[id] != id) {
                islands[id] = id;
                island++;
                for (int j = 0; j < 4; j++) {
                    int nx = operator.x + directions[j][0];
                    int ny = operator.y + directions[j][1];
                    int nid = ny * m + nx;
                    if (ny >= 0 && ny < n && nx >= 0 && nx < m && islands[nid] != -1) {
                        int root = root(nid);
                        if (root != id) {
                            islands[root] = id;
                            island--;
                        }
                    }
                }
            }
            res.add(island);
        }
        return res;
    }
}
