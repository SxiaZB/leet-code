package com.leet;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.IntConsumer;

public class LeetCode {

    /**
     * @link: https://leetcode-cn.com/problems/parsing-a-boolean-expression/
     */
    static class Solution1106 {
        public boolean parseBoolExpr(String expression) {
            return dfs(expression.toCharArray(), 0, '-') == 't';
        }

        private char dfs(char[] c, int i, char sign) {
            char cur = '-';
            while (i < c.length) {
                char nowC = c[i];
                if (nowC == '-' || nowC == ',' || nowC == '(') {
                    i++;
                    continue;
                }
                c[i] = '-';
                if (nowC == ')') {
                    break;
                }
                if (nowC == '!' || nowC == '|' || nowC == '&') {
                    nowC = dfs(c, i + 1, nowC);
                }
                if (cur == '-') {
                    cur = nowC;
                }
                switch (sign) {
                    case '!':
                        cur = !(nowC == 't') ? 't' : 'f';
                        break;
                    case '|':
                        cur = ((cur == 't') || (nowC == 't')) ? 't' : 'f';
                        break;
                    case '&':
                        cur = ((nowC == 't') && (cur == 't')) ? 't' : 'f';
                        break;
                    default:
                        cur = nowC;
                        break;
                }
                i++;
            }
            return cur;
        }
    }

    /**
     * @link: https://leetcode-cn.com/problems/decode-string/
     */
    static class Solution394 {
        public String decodeString(String s) {
            return dfs(s.toCharArray(), 0);
        }

        private String dfs(char[] c, int i) {
            StringBuilder cur = new StringBuilder();
            int swell = 0;
            while (i < c.length) {
                char nowC = c[i];
                if (nowC == '-') {
                    i++;
                    continue;
                }
                c[i] = '-';
                if (nowC >= '0' && nowC <= '9') {
                    swell = swell * 10 + (nowC - '0');
                } else if (nowC == '[') {
                    String dfs = dfs(c, i + 1);
                    while (swell > 0) {
                        cur.append(dfs);
                        swell--;
                    }
                } else if (nowC == ']') {
                    break;
                } else {
                    cur.append(nowC);
                }
                i++;
            }
            return cur.toString();
        }
    }

    /**
     * @link: https://leetcode-cn.com/problems/regular-expression-matching/
     */
    static class Solution10 {

        /*
        func isMatch(s string, p string) bool {
            m, n := len(s), len(p)
            dp := make([][]bool, m + 1)
            for i := 0; i <= m; i++ {
                dp[i] = make([]bool, n + 1)
            }

            dp[m][n] = true

            // 只能倒着来，因为a*这种pattern，先得找到*，再找到a，才能正确
            for i := m; i >= 0; i-- { // 留意从m开始，因为字符串是""也有可能匹配
                for j := n - 1; j >= 0; j-- {
                    curMatched := i < m && (s[i] == p[j] || p[j] == '.')
                    if j < n - 1 && p[j + 1] == '*' {
                        dp[i][j] = dp[i][j + 2] || (curMatched && dp[i + 1][j])
                    } else {
                        dp[i][j] = curMatched && dp[i+1][j+1]
                    }
                }
            }

            return dp[0][0]
        }*/

        public boolean isMatch(String s, String p) {
            if (s == null || p == null) {
                return false;
            }
            if (p.isEmpty()) {
                return s.isEmpty();
            }
            boolean first = s.length() > 0 && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.');
            if (p.length() >= 2 && p.charAt(1) == '*') {
                return (first && isMatch(s.substring(1), p)) || isMatch(s, p.substring(2));
            } else {
                return first && isMatch(s.substring(1), p.substring(1));
            }
        }
    }

    /**
     * @link: https://leetcode-cn.com/problems/implement-strstr/
     */
    static class Solution28 {
        public int strStr(String haystack, String needle) {
            if (needle == null || "".equals(needle)) {
                return 0;
            }
            int m = needle.length();
            int n = haystack.length();
            Map<Character, Integer> bmBc = new HashMap<>();
            int[] bmGs = new int[m];
            preBmBc(needle, m, bmBc);
            preBmGs(needle, m, bmGs);
            int i = 0;
            int j = 0;
            while (j <= n - m) {
                for (i = m - 1; i >= 0; i--) {
                    if (needle.charAt(i) != haystack.charAt(i + j)) {
                        break;
                    }
                }
                if (i < 0) {
                    return j;
                } else {
                    j += Math.max(bmGs[i], getBmBc(haystack.charAt(i + j), bmBc, m) - (m - 1 - i));
                }
            }
            return -1;
        }

        private void preBmBc(String pattern, int patLength, Map<Character, Integer> bmBc) {
            for (int i = patLength - 2; i >= 0; i--) {
                if (!bmBc.containsKey(pattern.charAt(i))) {
                    // 存入从右往左某个字符第一次出现距离尾部的距离
                    bmBc.put(pattern.charAt(i), patLength - 1 - i);
                }
            }
        }

        private int getBmBc(char c, Map<Character, Integer> bmBc, int m) {
            //取坏字符算法得到的位移，存在则返回位移
            if (bmBc.containsKey(c)) {
                return bmBc.get(c);
            } else {
                return m;
            }
        }

        private void suffix(String pattern, int patLength, int[] suffix) {
            //suffix[i] = s 表示以i为边界，与模式串后缀匹配的最大长度
            suffix[patLength - 1] = patLength;
            int q = 0;
            for (int i = patLength - 2; i >= 0; i--) {
                q = i;
                while (q >= 0 && pattern.charAt(q) == pattern.charAt(patLength - 1 - (i - q))) {
                    q--;
                }
                suffix[i] = i - q;
            }
        }

        private void preBmGs(String pattern, int patLength, int[] bmGs) {
            int i, j;
            int[] suffix = new int[patLength];
            suffix(pattern, patLength, suffix);
            //模式串中没有子串匹配上好后缀，也找不到一个最大前缀
            for (i = 0; i < patLength; i++) {
                bmGs[i] = patLength;
            }
            //模式串中没有子串匹配上好后缀，但找到一个最大前缀
            j = 0;
            for (i = patLength - 1; i >= 0; i--) {
                if (suffix[i] == i + 1) {
                    for (; j < patLength - 1 - i; j++) {
                        if (bmGs[j] == patLength) {
                            bmGs[j] = patLength - 1 - i;
                        }
                    }
                }
            }
            //模式串中有子串匹配上好后缀
            for (i = 0; i < patLength - 1; i++) {
                bmGs[patLength - 1 - suffix[i]] = patLength - 1 - i;
            }
        }
    }

    /**
     * @link: https://www.lintcode.com/problem/number-of-islands-ii/description
     */
    static class Solution434 {
        private int n, m;

        public List<Integer> numIslands2(int n, int m, Point[] operators) {
            List<Integer> res = new ArrayList<>();
            if (operators == null || operators.length == 0) {
                return res;
            }
            int[][] grid = new int[n][m];

            this.n = n;
            this.m = m;
            for (int i = 0; i < operators.length; i++) {
                Point operator = operators[i];
                if (operator.x < 0 || operator.x >= n || operator.y < 0 || operator.y >= m) {
                    //无效点击
                    res.add(i == 0 ? 0 : res.get(i - 1));
                    continue;
                }
                grid[operator.x][operator.y] = 1;
                res.add(numIslands(grid));
            }
            return res;
        }

        public int numIslands(int[][] grid) {
            int res = 0;
            boolean[][] click = new boolean[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (grid[i][j] == 1 && !click[i][j]) {
                        res++;
                        dfs(grid, click, i, j);
                    }
                }
            }

            return res;
        }

        private void dfs(int[][] grid, boolean[][] click, int r, int c) {
            if (r < 0 || c < 0 || r >= n || c >= m || click[r][c] || grid[r][c] == 0) {
                return;
            }

            click[r][c] = true;
            dfs(grid, click, r - 1, c);
            dfs(grid, click, r + 1, c);
            dfs(grid, click, r, c - 1);
            dfs(grid, click, r, c + 1);
        }
    }

    static class Solution434T2 {
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

    static class Solution1391 {
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

    /**
     * @link: https://leetcode-cn.com/problems/design-hashmap/
     */
    static class MyHashMap {

        class Node {
            int key;
            int val;
            Node next;

            public Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        private final int nodesLength = 8;

        private Node[] nodes;

        /**
         * Initialize your data structure here.
         */
        public MyHashMap() {
            nodes = new Node[nodesLength];
        }

        /**
         * value will always be non-negative.
         */
        public void put(int key, int value) {
            int index = key % nodesLength;
            Node previous = null;
            Node now = nodes[index];
            while (true) {
                if (now == null || now.key > key) {
                    Node newNode = new Node(key, value);
                    if (previous == null) {
                        newNode.next = now;
                        nodes[index] = newNode;
                    } else {
                        Node next = previous.next;
                        previous.next = newNode;
                        newNode.next = next;
                    }
                    return;
                } else if (now.key == key) {
                    now.val = value;
                    return;
                }
                previous = now;
                now = now.next;
            }
        }

        /**
         * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
         */
        public int get(int key) {
            int index = key % nodesLength;
            Node node = nodes[index];
            while (node != null) {
                if (node.key == key) {
                    return node.val;
                } else if (node.key > key) {
                    break;
                }
                node = node.next;
            }
            return -1;
        }

        /**
         * Removes the mapping of the specified value key if this map contains a mapping for the key
         */
        public void remove(int key) {
            int index = key % nodesLength;
            Node previous = null;
            Node now = nodes[index];
            while (now != null) {
                if (now.key == key) {
                    if (previous == null) {
                        nodes[index] = null;
                    } else {
                        previous.next = now.next;
                    }
                    now.next = null;
                    return;
                } else if (now.key > key) {
                    return;
                }
                previous = now;
                now = now.next;
            }
        }
    }

    /**
     * @link https://leetcode-cn.com/problems/design-skiplist/
     */
    static class Skiplist {

        /**
         * 可优化的点：
         * 1、不用保存多个 key 和 val
         * 2、重复值可以不用插入多个，记录一个 count 就行
         */
        /**
         * @code：go
         * @author：邢剑宽 import "math/rand"
         * <p>
         * type Node struct {
         * Nexts  []*Node
         * Val    int
         * DupCnt int // 同样的值的个数
         * }
         * <p>
         * func NewNode(Val int, Levels int) *Node {
         * return &Node{
         * Nexts:  make([]*Node, Levels),
         * Val:    Val,
         * DupCnt: 1,
         * }
         * }
         * <p>
         * type Skiplist struct {
         * head *Node
         * }
         * <p>
         * func Constructor() Skiplist {
         * return Skiplist{
         * head: NewNode(-1, MAX_LEVELS),
         * }
         * }
         * <p>
         * const MAX_LEVELS int = 10
         * <p>
         * func randLevel() int {
         * levels := 1
         * <p>
         * // 每次level再增加一层的概率会缩小一半
         * for rand.Int()%2 == 1 && levels < MAX_LEVELS {
         * levels++
         * }
         * return levels
         * }
         * <p>
         * func (this *Skiplist) Search(target int) bool {
         * <p>
         * level := len(this.head.Nexts) - 1
         * p := this.head
         * <p>
         * for level >= 0 {
         * next := p.Nexts[level]
         * if next != nil && next.Val < target {
         * p = next
         * } else if next != nil && next.Val == target {
         * return true
         * } else {
         * level--
         * }
         * }
         * <p>
         * return false
         * }
         * <p>
         * func (this *Skiplist) Add(num int) {
         * <p>
         * // 假设会创建新节点的时候的层数，这样要从一开始就把所有的前置层数开始遍历。当然，如果发现已经节点有这个值了，就保持原节点不变，把DupCnt增加即可
         * levels := randLevel()
         * <p>
         * curs := make([]*Node, levels)
         * level := levels - 1
         * <p>
         * p := this.head
         * for level >= 0 { //用和Search一摸一样的方式去找
         * next := p.Nexts[level]
         * if next != nil && next.Val < num {
         * p = next
         * } else if next != nil && next.Val == num {
         * // 找到相同的点，直接增加DupCnt即可完成了
         * next.DupCnt++
         * return
         * } else {
         * curs[level] = p
         * level--
         * }
         * }
         * <p>
         * // 找不到节点，就增加一个新的
         * node := NewNode(num, levels)
         * for i := 0; i < levels; i++ {
         * next := curs[i].Nexts[i]
         * node.Nexts[i] = next
         * curs[i].Nexts[i] = node
         * }
         * }
         * <p>
         * func (this *Skiplist) Erase(num int) bool {
         * <p>
         * // 从最高层搜索，找到指定节点的前面的节点，如果是DupCnt > 1 直接--即可。否则把所有前寄调整即可
         * <p>
         * curs := make([]*Node, MAX_LEVELS)
         * level :=  MAX_LEVELS - 1
         * <p>
         * p := this.head
         * <p>
         * for level >= 0 {
         * next := p.Nexts[level]
         * if next != nil && next.Val < num {
         * p = next
         * } else if next != nil && next.Val == num && next.DupCnt > 1 {
         * next.DupCnt--
         * return true
         * } else {
         * curs[level] = p
         * level--
         * }
         * }
         * <p>
         * found := false
         * for i := 0; i < MAX_LEVELS; i++ {
         * next := curs[i].Nexts[i]
         * if next == nil || next.Val != num {
         * continue
         * } else {
         * curs[i].Nexts[i] = next.Nexts[i]
         * found = true
         * }
         * }
         * <p>
         * return found
         * }
         */
        class SkipNode {
            public int val;
            public SkipNode left;
            public SkipNode right;
            public SkipNode[] now;

            public SkipNode(int val, SkipNode[] now) {
                this.val = val;
                this.now = now;
            }
        }

        private int getLevel() {
            int level = 1;
            while (level < maxLevel && (int) (Math.random() * 2 + 1) == 1) {
                level++;
            }
            return level;
        }

        private final int maxLevel = 8;
        public SkipNode[] head;

        public Skiplist() {
            head = new SkipNode[maxLevel];
            for (int i = 0; i < maxLevel; i++) {
                head[i] = new SkipNode(-1, head);
            }
        }

        public boolean search(int target) {
            return getTargetNodes(target) != null;
        }

        public void add(int num) {
            int level = getLevel();
            SkipNode[] nowNodes = new SkipNode[level];
            SkipNode[] leftNodes = head;
            int i = level - 1;
            while (i >= 0) {
                SkipNode nowNode = new SkipNode(num, nowNodes);
                nowNodes[i] = nowNode;
                SkipNode leftNode = leftNodes[i];
                SkipNode rightNode = leftNode.right;
                while (rightNode != null && rightNode.val < nowNode.val) {
                    leftNode = rightNode;
                    rightNode = leftNode.right;
                    leftNodes = leftNode.now;
                }
                leftNode.right = nowNode;
                nowNode.left = leftNode;
                nowNode.right = rightNode;
                if (rightNode != null) {
                    rightNode.left = nowNode;
                }
                i--;
            }
        }

        public boolean erase(int num) {
            SkipNode[] targetNodes = getTargetNodes(num);
            if (targetNodes == null) {
                return false;
            }
            int i = targetNodes.length - 1;
            while (i >= 0) {
                SkipNode targetNode = targetNodes[i];
                SkipNode leftNode = targetNode.left;
                SkipNode rightNode = targetNode.right;
                leftNode.right = rightNode;
                if (rightNode != null) {
                    rightNode.left = leftNode;
                }
                i--;
            }
            return true;
        }

        private SkipNode[] getTargetNodes(int num) {
            SkipNode[] targetNodes = null;
            SkipNode[] nowNodes = head;
            int i = nowNodes.length - 1;
            while (i >= 0) {
                SkipNode nowNode = nowNodes[i];
                if (nowNode.val == num) {
                    targetNodes = nowNodes;
                    break;
                } else if (nowNode.val < num && nowNode.right != null && nowNode.right.val <= num) {
                    nowNodes = nowNode.right.now;
                } else {
                    i--;
                }
            }
            return targetNodes;
        }

    }

    class Solution33 {
        public int search(int[] nums, int target) {
            return recursive(nums, target, 0, nums.length - 1);
        }

        public int recursive(int[] nums, int target, int l, int r) {
            if (l > r || (nums[l] < nums[r] && (nums[l] > target || nums[r] < target))) {
                return -1;
            }
            if (nums[l] < nums[r]) {
                while (l <= r) {
                    int i = (r + l) / 2;
                    if (nums[i] == target) {
                        return i;
                    } else if (nums[i] < target) {
                        l = i + 1;
                    } else {
                        r = i - 1;
                    }
                }
                return -1;
            } else {
                int i = (r + l) / 2;
                if (nums[i] == target) {
                    return i;
                } else {
                    return Math.max(recursive(nums, target, l, i - 1), recursive(nums, target, i + 1, r));
                }
            }
        }
    }

    class Solution704 {
        public int search(int[] nums, int target) {
            int l = 0;
            int r = nums.length - 1;
            while (l <= r) {
                int i = (r + l) / 2;
                if (nums[i] == target) {
                    return i;
                } else if (nums[i] < target) {
                    l = i + 1;
                } else {
                    r = i - 1;
                }
            }
            return -1;
        }
    }

    /**
     * @url https://leetcode-cn.com/problems/design-twitter/
     * @title 设计推特（355题）
     * <p>
     * Your Twitter object will be instantiated and called as such:
     * Twitter obj = new Twitter();
     * obj.postTweet(userId,tweetId);
     * List<Integer> param_2 = obj.getNewsFeed(userId);
     * obj.follow(followerId,followeeId);
     * obj.unfollow(followerId,followeeId);
     */
    static class Twitter {

        private Map<Integer, HashSet<Integer>> followMap;
        private List<Integer> tweetList;
        private Map<Integer, HashSet<Integer>> tweetListIndexMap;

        /**
         * Initialize your data structure here.
         */
        public Twitter() {
            followMap = new HashMap<>();
            tweetList = new ArrayList<>();
            tweetListIndexMap = new HashMap<>();
        }

        /**
         * Compose a new tweet.
         */
        public void postTweet(int userId, int tweetId) {
            tweetList.add(tweetId);
            HashSet<Integer> userTweetListIndexSet = tweetListIndexMap.computeIfAbsent(userId, k -> new HashSet<>());
            userTweetListIndexSet.add(tweetList.size() - 1);
        }

        /**
         * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
         */
        public List<Integer> getNewsFeed(int userId) {
            HashSet<Integer> followeeIds = followMap.get(userId);
            if (followeeIds == null) {
                followeeIds = new HashSet<>();
            }
            List<Integer> feedList = new ArrayList<>();
            HashSet<Integer> myTwitterList = tweetListIndexMap.get(userId);
            if (myTwitterList != null) {
                feedList.addAll(myTwitterList);
            }
            for (Iterator<Integer> iterator = followeeIds.iterator(); iterator.hasNext(); ) {
                HashSet<Integer> followeeTwitterList = tweetListIndexMap.get(iterator.next());
                if (followeeTwitterList != null) {
                    feedList.addAll(followeeTwitterList);
                }
            }
            feedList.sort((a, b) -> b - a);

            List<Integer> newsFeedList = new ArrayList<>(10);
            for (int i = 0; i < 10 && i < feedList.size(); i++) {
                newsFeedList.add(i, tweetList.get(feedList.get(i)));
            }
            return newsFeedList;
        }

        /**
         * Follower follows a followee. If the operation is invalid, it should be a no-op.
         */
        public void follow(int followerId, int followeeId) {
            if (followeeId == followerId) {
                return;
            }
            HashSet<Integer> followeeSet = followMap.computeIfAbsent(followerId, k -> new HashSet<>());
            followeeSet.add(followeeId);
        }

        /**
         * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
         */
        public void unfollow(int followerId, int followeeId) {
            HashSet<Integer> followeeSet = followMap.get(followerId);
            if (followeeSet != null) {
                followeeSet.remove(followeeId);
            }
        }
    }

    class Solution529 {
        private final char e = 'E';
        private final char m = 'M';
        private final char x = 'X';
        private final char b = 'B';
        private Queue<int[]> queue = new LinkedList<>();

        int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
        int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

        public char[][] updateBoard(char[][] board, int[] click) {
            queue.offer(click);
            while (!queue.isEmpty()) {
                click = queue.poll();
                int i = click[0];
                int j = click[1];
                char now = board[i][j];
                if (now == m) {
                    board[i][j] = x;
                } else if (now == e) {
                    int count = 0;
                    Queue<int[]> q = new LinkedList<>();
                    for (int d = 0; d < 8; d++) {
                        int newX = i + dx[d];
                        int newY = j + dy[d];
                        if (newX < 0 || newX >= board.length || newY < 0 || newY >= board[0].length) {
                            continue;
                        }
                        count = getCount(board[newX][newY], newX, newY, count, q);
                    }
                    if (count == 0) {
                        board[i][j] = b;
                        while (!q.isEmpty()) {
                            queue.offer(q.poll());
                        }
                    } else {
                        board[i][j] = (char) (count + 48);
                    }
                }
            }
            return board;
        }

        private void recursive(char[][] board) {
            int[] click = queue.poll();
            if (click == null) {
                return;
            }
            int i = click[0];
            int j = click[1];
            char now = board[i][j];
            if (now == m) {
                board[i][j] = x;
            } else if (now == e) {
                int count = 0;
                Queue<int[]> q = new LinkedList<>();
                for (int d = 0; d < 8; d++) {
                    int newX = i + dx[d];
                    int newY = j + dy[d];
                    if (newX < 0 || newX >= board.length || newY < 0 || newY >= board[0].length) {
                        continue;
                    }
                    count = getCount(board[newX][newY], newX, newY, count, q);
                }
                if (count == 0) {
                    board[i][j] = b;
                    while (!q.isEmpty()) {
                        queue.offer(q.poll());
                    }
                } else {
                    board[i][j] = (char) (count + 48);
                }
            }
            recursive(board);
        }

        private int getCount(char c, int i, int j, int count, Queue<int[]> q) {
            if (c == m) {
                count++;
            } else if (c == e) {
                q.offer(new int[]{i, j});
            }
            return count;
        }
//        public int getCount(char[][] board, int x, int y) {
//            int r = board.length;
//            int c = board[0].length;
//            int count = 0;
//            for (int i = 0; i < 8; i++) {
//                int newX = x + dx[i];
//                int newY = y + dy[i];
//                if (newX < 0 || newX >= r || newY < 0 || newY >= c) {
//                    continue;
//                }
//                if (board[newX][newY] == 'M') {
//                    count++;
//                }
//            }
//            return count;
//        }

    }

    class Solution101 {
        public boolean isSymmetric(TreeNode root) {
            if (root == null) {
                return true;
            }
            return recursive(root.left, root.right);
        }

        public boolean recursive(TreeNode l, TreeNode r) {
            if (l == null && r == null) {
                return true;
            }
            if (l == null || r == null || l.val != r.val) {
                return false;
            }
            return recursive(l.left, r.right) && recursive(l.right, r.left);
        }

    }

    class Solution64 {
        public int minPathSum(int[][] grid) {
            if (grid == null || grid.length == 0) {
                return 0;
            }
            int m = grid.length;
            int n = grid[0].length;
            int[] dp = grid[0];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    } else if (i == 0) {
                        dp[j] += dp[j - 1];
                    } else if (j == 0) {
                        dp[j] += grid[i][j];
                    } else {
                        if (dp[j] < dp[j - 1]) {
                            dp[j] += grid[i][j];
                        } else {
                            dp[j] = grid[i][j] + dp[j - 1];
                        }
                    }
                }
            }
            return dp[n - 1];
        }
    }

    class Solution62 {
        public int uniquePaths(int m, int n) {
            int[] dp = new int[m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (i == 0) {
                        dp[j] = 1;
                    } else if (j != 0) {
                        dp[j] += dp[j - 1];
                    }
                }
            }
            return dp[m - 1];
        }
    }

    class Solution494 {

        private int res = 0;

        /**
         * 思路就是把整个集合拆分成两个子集，Q表示整个集合，P表示正数子集，N表示负数子集， T表示目标和，用S(X)表示集合的求和函数，集合中均为非负数，N集合是指选中这部分元素作为负数子集。
         * <p>
         * S(P) - S(N) = T
         * S(P)−S(N)=T
         * <p>
         * S(P) + S(N) + S(P) - S(N) = T + S(P) + S(N)
         * S(P)+S(N)+S(P)−S(N)=T+S(P)+S(N)
         * <p>
         * 2S(P) = S(Q) + T
         * 2S(P)=S(Q)+T
         * <p>
         * 也就是：正数集的和的两倍 == 等于目标和 + 序列总和
         * <p>
         * 所以问题就转换成了，找到一个正数集P，其和的两倍等于目标和+序列总和
         */
        public int findTargetSumWays(int[] nums, int S) {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }
            if ((S + sum) % 2 != 0 || S > sum) {
                return 0;
            }
            S = (S + sum) / 2;
            int[] dp = new int[S + 1];
            dp[0] = 1;
            for (int i = 0; i < nums.length; i++) {
                //当遍历第i个元素时，dp[x]表示在前i个元素的序列中，选择部分或者全部元素（不重复使用），能得到和为x的组合有多少种；
                // 如：前i=2个元素为1、2时，dp[0]=1、dp[1]=1、dp[2]=1、dp[3]=1
                //当遍历第i+1元素（num[i]）时，dp[x]等于dp[x-num[i]]的种数，加上前i个元素得到的dp[x]种数
                for (int j = S; j >= nums[i]; j--) {
                    dp[j] += dp[j - nums[i]];
                }
            }
            return dp[S];
        }

        public int findTargetSumWays1(int[] nums, int S) {
            if (S > 1000) {
                return 0;
            }
            int[][] dp = new int[nums.length + 1][2001];
            //将1000当0点，设置初始值为1
            dp[0][1000] = 1;
            for (int i = 0; i < nums.length; i++) {
                for (int sum = -1000; sum <= 1000; sum++) {
                    if (dp[i][sum + 1000] > 0) {
                        dp[i + 1][sum + nums[i] + 1000] += dp[i][sum + 1000];
                        dp[i + 1][sum - nums[i] + 1000] += dp[i][sum + 1000];
                    }
                }
            }
            return dp[nums.length][1000 + S];
        }

        public int findTargetSumWays2(int[] nums, int S) {
            recursive(nums, 0, 0, S);
            return res;
        }

        private void recursive(int[] nums, int now, int sum, int S) {
            if (now >= nums.length) {
                if (sum == S) {
                    res = res + 1;
                }
                return;
            }
            recursive(nums, now + 1, sum + nums[now], S);
            recursive(nums, now + 1, sum - nums[now], S);
        }
    }

    class Solution473 {
        private int[] sums = new int[4];
        private int side;

        public boolean makesquare(int[] nums) {
            if (nums.length < 4) {
                return false;
            }
            Arrays.sort(nums);
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum = sum + nums[i];
            }
            if (sum % 4 != 0) {
                return false;
            }
            side = sum / 4;
            if (nums[nums.length - 1] > side) {
                return false;
            }
            return recursive(nums.length - 1, nums);
        }

        private boolean recursive(int now, int[] nums) {
            if (now < 0) {
                return sums[0] == side && sums[1] == side && sums[2] == side;
            }
            for (int i = 0; i < 4; i++) {
                if (sums[i] + nums[now] <= side) {
                    sums[i] = sums[i] + nums[now];
                    if (recursive(now - 1, nums)) {
                        return true;
                    }
                    sums[i] = sums[i] - nums[now];
                }
            }
            return false;
        }
    }

    /**
     * @link: https://leetcode-cn.com/problems/implement-trie-prefix-tree/
     */
    class Trie {

        private TrieNote root;

        /**
         * Initialize your data structure here.
         */
        public Trie() {
            root = new TrieNote();
        }

        /**
         * Inserts a word into the trie.
         */
        public void insert(String word) {
            TrieNote note = root;
            int length = word.length();
            for (int i = 0; i < length; i++) {
                note = note.put(word.charAt(i));
            }
            note.setEnd(true);
        }

        /**
         * Returns if the word is in the trie.
         */
        public boolean search(String word) {
            TrieNote note = root;
            int length = word.length();
            for (int i = 0; i < length; i++) {
                note = note.get(word.charAt(i));
                if (note == null) {
                    return false;
                }
            }
            return note.isEnd();
        }

        /**
         * Returns if there is any word in the trie that starts with the given prefix.
         */
        public boolean startsWith(String prefix) {
            TrieNote note = root;
            int length = prefix.length();
            for (int i = 0; i < length; i++) {
                note = note.get(prefix.charAt(i));
                if (note == null) {
                    return false;
                }
            }
            return note != null;
        }

        class TrieNote {
            private boolean end;
            private TrieNote[] notes;

            private int length = 26;

            public TrieNote() {
                notes = new TrieNote[length];
            }

            public boolean isEnd() {
                return end;
            }

            public void setEnd(boolean end) {
                this.end = end;
            }

            public TrieNote put(char c) {
                int i = c - 'a';
                if (notes[i] == null) {
                    TrieNote note = new TrieNote();
                    notes[i] = note;
                }
                return notes[i];
            }

            public TrieNote get(char c) {
                int i = c - 'a';
                return notes[i];
            }
        }
    }

    class WordDictionary {
        private TrieNote root;

        private final char m = '.';

        /**
         * Initialize your data structure here.
         */
        public WordDictionary() {
            root = new TrieNote();
        }

        /**
         * Adds a word into the data structure.
         */
        public void addWord(String word) {
            TrieNote note = root;
            int length = word.length();
            for (int i = 0; i < length; i++) {
                note = note.put(word.charAt(i));
            }
            note.setEnd(true);
        }

        /**
         * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
         */
        public boolean search(String word) {
            return recursive(root, word, 0);
        }

        private boolean recursive(TrieNote note, String word, int now) {
            if (now >= word.length()) {
                return note.isEnd();
            }
            char nc = word.charAt(now);
            if (nc == m) {
                TrieNote[] notes = note.getNotes();
                for (TrieNote trieNote : notes) {
                    if (trieNote != null && recursive(trieNote, word, now + 1)) {
                        return true;
                    }
                }
                return false;
            } else {
                TrieNote trieNote = note.get(nc);
                return trieNote != null && recursive(trieNote, word, now + 1);
            }
        }

        class TrieNote {
            private boolean end;

            private TrieNote[] notes;

            private int length = 26;

            public TrieNote() {
                notes = new TrieNote[length];
            }

            public boolean isEnd() {
                return end;
            }

            public void setEnd(boolean end) {
                this.end = end;
            }

            public TrieNote put(char c) {
                int i = c - 'a';
                if (notes[i] == null) {
                    TrieNote note = new TrieNote();
                    notes[i] = note;
                }
                return notes[i];
            }

            public TrieNote get(char c) {
                int i = c - 'a';
                return notes[i];
            }

            public TrieNote[] getNotes() {
                return notes;
            }
        }
    }

    class Solution318 {
        public int maxProduct(String[] words) {
            int i = 0, j = 0;
            int length = words.length;
            Arrays.sort(words, (a, b) -> b.length() - a.length());
            for (; i < length; i++) {
                j = i + 1;
                for (; j < length; j++) {
                    if (!isRepeat(words[i], words[j])) {
                        return words[i].length() * words[j].length();
                    }
                }
            }
            return 0;
        }

        public boolean isRepeat(String i, String j) {
            int length = i.length();
            for (int k = 0; k < length; k++) {
                if (j.indexOf(i.codePointAt(k)) > -1) {
                    return true;
                }
            }
            return false;
        }
    }

    class Solution139 {
        private Map<String, Boolean> map = new HashMap<>();

        public boolean wordBreak(String s, List<String> wordDict) {
            int length = s.length();
            boolean[] b = new boolean[length + 1];
            b[0] = true;
            for (int i = 0; i < length; i++) {
                for (int j = i + 1; j <= length; j++) {
                    if (b[i] && wordDict.contains(s.substring(i, j))) {
                        b[j] = true;
                    }
                }
            }
            return b[length];
        }
    }

    class Solution15 {
        public List<List<Integer>> threeSum(int[] nums) {
            int compare = 0;
            List<List<Integer>> res = new ArrayList<>();
            if (nums == null || nums.length < 3) {
                return res;
            }
            Arrays.sort(nums);
            int length = nums.length;
            for (int i = 0; i < length; i++) {
                //如果当前数字大于需要相等的值，则结束循环
                if (nums[i] > compare) break;
                // 如果当前数字和上一个数字相同，则跳过该次循环
                if (i > 0 && nums[i] == nums[i - 1]) continue;
                int l = i + 1;
                int r = length - 1;
                while (l < r) {
                    int sum = nums[i] + nums[l] + nums[r];
                    if (sum == compare) {
                        res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                        while (l < r && nums[l] == nums[l + 1]) {
                            // 去重
                            l++;
                        }
                        while (l < r && nums[r] == nums[r - 1]) {
                            // 去重
                            r--;
                        }
                        l++;
                        r--;
                    } else if (sum > compare) {
                        r--;
                    } else {
                        l++;
                    }
                }
            }
            return res;
        }
    }

    class Solution16 {
        public int threeSumClosest(int[] nums, int target) {
            if (nums == null || nums.length < 3) {
                throw new RuntimeException("数组不满足条件！");
            }
            Arrays.sort(nums);
            int res = nums[0] + nums[1] + nums[2];
            int length = nums.length;
            for (int i = 0; i < nums.length - 2; i++) {
                int l = i + 1;
                int r = length - 1;
                while (l < r) {
                    int sum = nums[i] + nums[l] + nums[r];
                    System.out.println(sum);
                    if (Math.abs(target - sum) < Math.abs(target - res)) {
                        res = sum;
                    }
                    if (sum > target) {
                        r--;
                    } else if (sum < target) {
                        l++;
                    } else {
                        return sum;
                    }
                }
            }
            return res;
        }

        public int threeSumClosest1(int[] nums, int target) {
            Arrays.sort(nums);
            int result = nums[0] + nums[1] + nums[2];
            for (int i = 0; i < nums.length - 2; i++) {
                int left = i + 1;
                int right = nums.length - 1;
                while (left != right) {
                    int min = nums[i] + nums[left] + nums[left + 1];
                    if (target < min) {
                        if (Math.abs(result - target) > Math.abs(min - target))
                            result = min;
                        break;
                    }
                    int max = nums[i] + nums[right] + nums[right - 1];
                    if (target > max) {
                        if (Math.abs(result - target) > Math.abs(max - target))
                            result = max;
                        break;
                    }
                    int sum = nums[i] + nums[left] + nums[right];
                    // 判断三数之和是否等于target
                    if (sum == target)
                        return sum;
                    if (Math.abs(sum - target) < Math.abs(result - target))
                        result = sum;
                    if (sum > target) {
                        right--;
                        while (left != right && nums[right] == nums[right + 1])
                            right--;
                    } else {
                        left++;
                        while (left != right && nums[left] == nums[left - 1])
                            left++;
                    }
                }
                while (i < nums.length - 2 && nums[i] == nums[i + 1])
                    i++;
            }
            return result;
        }
    }

    class Solution98 {
        // public boolean isValidBST(TreeNode root) {
        //     return recursiveIsValidBST(root,null,null);
        // }

        // //从下往上遍历，判断当前节点的值是否满足以下规律：
        // //如果该节点是父节点的左节点，并且父节点是祖父节点的左节点，此时需要满足该节点小于父节点；
        // //如果该节点是父节点的右节点，并且父节点是祖父节点的左节点，此时需要满足该节点大于父节点且小于祖父节点；
        // //如果该节点是父节点的左节点，并且父节点是祖父节点的右节点，此时需要满足该节点小于父节点且大于祖父节点；
        // //如果该节点是父节点的右节点，并且父节点是祖父节点的右节点，此时需要满足该节点大于父节点；
        // //如果该节点的父节点或祖父节点为空，则认为满足为空的那部分规律。
        // public boolean recursiveIsValidBST(TreeNode node,Integer low,Integer high){
        //     if(node==null){
        //         return true;
        //     }
        //     int now = node.val;
        //     if(low!=null&&low>=now){
        //         return false;
        //     }
        //     if(high!=null&&high<=now){
        //         return false;
        //     }
        //     boolean left = recursiveIsValidBST(node.left,low,now);
        //     if(!left){
        //         return false;
        //     }
        //     boolean right = recursiveIsValidBST(node.right,now,high);
        //     if(!right){
        //         return false;
        //     }
        //     return true;
        // }


        public boolean isValidBST(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            inOrderTraversal(root, list);
            int n = list.size();
            for (int i = 0; i < n - 1; i++) {
                if (list.get(i) >= list.get(i + 1)) {
                    return false;
                }
            }
            return true;
        }

        //利用二叉搜索树的中序遍历是从小到大的顺序，左>中>右，递归到左节点的最末端，将左节点添加到list后，再添加本身，再遍历右节点
        public void inOrderTraversal(TreeNode node, List<Integer> list) {
            if (node == null) {
                return;
            }
            inOrderTraversal(node.left, list);
            list.add(node.val);
            inOrderTraversal(node.right, list);
        }

    }

    class Solution110T1 {
        public boolean isBalanced(TreeNode root) {
            return helper(root) > -1;
        }

        private int helper(TreeNode node) {
            if (node == null) {
                return 0;
            }
            int left = helper(node.left);
            if (left < 0) {
                return -1;
            }
            int right = helper(node.right);
            if (right < 0) {
                return -1;
            }
            if (Math.abs(left - right) > 1) {
                return -1;
            }
            return Math.max(left, right) + 1;
        }
    }

    class Solution110T2 {
        public boolean isBalanced(TreeNode root) {
            Map<String, Integer> height = new HashMap<>(3);
            return helper(root, height);
        }

        private boolean helper(TreeNode node, Map<String, Integer> height) {
            if (node == null) {
                height.put("now", 0);
                return true;
            }
            if (!helper(node.left, height)) {
                return false;
            }
            height.put("left", height.get("now"));
            Integer left = height.get("left");
            if (!helper(node.right, height)) {
                return false;
            }
            height.put("right", height.get("now"));
            Integer right = height.get("right");
            if (Math.abs(left - right) > 1) {
                return false;
            }
            height.put("now", Math.max(left, right) + 1);
            return true;
        }
    }

    class SolutionPermute {

        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            int n = nums.length;
            List<Integer> numList = new ArrayList<>(n);
            //需要先将数组转化为Integer的list
            for (int i = 0; i < n; i++) {
                numList.add(nums[i]);
            }
            //先确定0下标的数
            recursivePermute(res, numList, 0, n - 1);
            return res;
        }

        //now为当前正在排列的下标,end为数组最后一个下标
        public void recursivePermute(List<List<Integer>> res, List<Integer> numList, int now, int end) {
            if (now == end) {
                //当now=end时，说明只剩最后一个数，没得选了，本次排列结束，将当前结果保存下来，添加到res中
                res.add(new ArrayList<>(numList));
            } else {
                //如果不等于，便将未排列的数，依次放在当前排列的下标中
                for (int i = now; i <= end; i++) {
                    //选择第i个排在第now下标
                    Collections.swap(numList, i, now);
                    //递归now+1个
                    recursivePermute(res, numList, now + 1, end);
                    //为了保证排列不重复，需要还原数组为原来的排序，因为选择第i个排列的情况还有别的
                    Collections.swap(numList, i, now);
                }
            }
        }
    }

    class KthLargest {

        int[] heap;

        //heap数组中实际存放的元素数量
        int size = 0;

        public int add(int val) {
            if (size < heap.length) {
                size++;
                heap[size - 1] = val;

                if (size == heap.length) {
                    //构建小顶堆
                    makeMinHeap();
                }

            } else {
                if (heap[0] < val) {
                    //替换堆顶元素
                    heap[0] = val;
                    minHeapFixdown(0);
                }
            }
            return heap[0];
        }

        /**
         * 堆化heap数组，建立最小堆
         */
        private void makeMinHeap() {
            int length = heap.length;
            //第一个非叶子节点是(length / 2) - 1
            for (int i = (length / 2) - 1; i >= 0; i--) {
                minHeapFixdown(i);
            }
        }

        /**
         * 从i节点开始调整, i节点的子节点为 2*i+1, 2*i+2
         *
         * @param i 第i个节点
         */
        public void minHeapFixdown(int i) {
            int temp = heap[i];
            //子节点是多少？i节点的子节点为 2*i+1, 2*i+2
            int subLeft = 2 * i + 1;
            while (subLeft < heap.length) {
                int subRight = subLeft + 1;
                if (subRight < heap.length && heap[subLeft] > heap[subRight]) {
                    subLeft++;
                }

                if (heap[subLeft] >= heap[i]) {
                    break;
                }
                heap[i] = heap[subLeft];
                heap[subLeft] = temp;

                i = subLeft;
                subLeft = 2 * i + 1;
            }

        }

        public int findKthLargest(int[] nums, int k) {
            heap = new int[k];
            for (int num : nums) {
                add(num);
            }
            return heap[0];
        }

    }

    class Solution109T1 {

        ListNode c;

        public TreeNode sortedListToBST(ListNode head) {
            if (head == null) {
                return null;
            }
            int length = 0;
            ListNode node = head;
            while (node != null) {
                length++;
                node = node.next;
            }
            c = head;
            return helper(0, length - 1);
        }

        private TreeNode helper(int s, int e) {
            if (s > e) {
                return null;
            }
            int m = s + ((e - s + 1) >> 1);
            TreeNode left = helper(s, m - 1);
            TreeNode node = new TreeNode(c.val);
            node.left = left;
            c = c.next;
            node.right = helper(m + 1, e);
            return node;
        }

    }

    class Solution109T2 {
        public TreeNode sortedListToBST(ListNode head) {
            if (head == null) {
                return null;
            }
            int length = 0;
            Map<Integer, TreeNode> treeNodeMap = new HashMap<>();
            ListNode node = head;
            while (node != null) {
                treeNodeMap.put(length, new TreeNode(node.val));
                length++;
                node = node.next;
            }
            return helper(head, length, length / 2 + 1);
        }

        private TreeNode helper(ListNode head, int nodeLength, int rootIndex) {
            if (nodeLength <= 0 || rootIndex > nodeLength) {
                return null;
            }
            ListNode tmp = head;
            int index = 1;
            while (index < rootIndex) {
                tmp = tmp.next;
                index++;
            }
            TreeNode node = new TreeNode(tmp.val);
            node.left = helper(head, index - 1, (index - 1) / 2 + 1);
            node.right = helper(tmp.next, nodeLength - rootIndex, (nodeLength - rootIndex) / 2 + 1);
            return node;
        }
    }

    class Solution21 {
        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            ListNode l0, res;
            if (l1 == null) {
                return l2;
            }
            if (l2 == null) {
                return l1;
            }

            if (l1.val < l2.val) {
                l0 = l1;
                l1 = l1.next;
            } else {
                l0 = l2;
                l2 = l2.next;
            }
            res = l0;

            while (l1 != null || l2 != null) {
                if (l1 == null) {
                    l0.next = l2;
                    break;
                } else if (l2 == null) {
                    l0.next = l1;
                    break;
                } else if (l1.val < l2.val) {
                    l0.next = l1;
                    l1 = l1.next;
                    l0 = l0.next;
                } else {
                    l0.next = l2;
                    l2 = l2.next;
                    l0 = l0.next;
                }
            }
            return res;
        }
    }

    class Solution23 {
        public ListNode mergeKLists(ListNode[] lists) {
            ListNode res = null;
            for (int i = 0; i < lists.length; i++) {
                res = mergeTwoLists(res, lists[i]);
            }
            return res;
        }

        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            ListNode l0, res;
            if (l1 == null) {
                return l2;
            }
            if (l2 == null) {
                return l1;
            }

            if (l1.val < l2.val) {
                l0 = l1;
                l1 = l1.next;
            } else {
                l0 = l2;
                l2 = l2.next;
            }
            res = l0;

            while (l1 != null || l2 != null) {
                if (l1 == null) {
                    l0.next = l2;
                    break;
                } else if (l2 == null) {
                    l0.next = l1;
                    break;
                } else if (l1.val < l2.val) {
                    l0.next = l1;
                    l1 = l1.next;
                    l0 = l0.next;
                } else {
                    l0.next = l2;
                    l2 = l2.next;
                    l0 = l0.next;
                }
            }
            return res;
        }
    }

    class Solution826 {

        class Job {
            private int difficulty;
            private int profit;

            public Job(int difficulty, int profit) {
                this.difficulty = difficulty;
                this.profit = profit;
            }
        }

        public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
            List<Job> jobs = new ArrayList<>(difficulty.length);
            for (int i = 0; i < difficulty.length; i++) {
                jobs.add(new Job(difficulty[i], profit[i]));
            }
            jobs.sort(Comparator.comparingInt(a -> a.difficulty));
            Arrays.sort(worker);
            int res = 0;
            int j = 0;
            int minProfit = 0;
            for (int i = 0; i < worker.length; i++) {
                int work = worker[i];
                for (; jobs.size() > j && work >= jobs.get(j).difficulty; j++) {
                    if (jobs.get(j).profit > minProfit) {
                        minProfit = jobs.get(j).profit;
                    }
                }
                res = res + minProfit;
            }

            return res;
        }

        private int getMaxProfit(int[] difficulty, int[] profit, int work) {
            int res = 0;
            for (int i = 0; i < difficulty.length; i++) {
                if (difficulty[i] <= work && profit[i] > res) {
                    res = profit[i];
                }
            }
            return res;
        }
    }

    class FizzBuzz {
        private int n;

        private int index = 1;
        //sync=-1 结束
        //sync=0 跳过
        //sync=1 同时不能被3和5整除
        //sync=3 只能被3整除
        //sync=5 只能被5整除
        //sync=15 同时被3和5整除
        private volatile int sync = 1;

        public FizzBuzz(int n) {
            this.n = n;
            if (n < 1) sync = -1;
        }

        private void next() {
            index++;
            if (index > n) {
                sync = -1;
            } else if (index % 15 == 0) {
                sync = 15;
            } else if (index % 5 == 0) {
                sync = 5;
            } else if (index % 3 == 0) {
                sync = 3;
            } else {
                sync = 1;
            }
        }

        private void setSync() {

        }

        // printFizz.run() outputs "fizz".
        public void fizz(Runnable printFizz) throws InterruptedException {
            while (sync >= 0) {
                if (sync == 3) {
                    printFizz.run();
                    next();
                }
                Thread.sleep(1);
            }
        }

        // printBuzz.run() outputs "buzz".
        public void buzz(Runnable printBuzz) throws InterruptedException {
            while (sync >= 0) {
                if (sync == 5) {
                    printBuzz.run();
                    next();
                }
                Thread.sleep(1);
            }
        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            while (sync >= 0) {
                if (sync == 15) {
                    printFizzBuzz.run();
                    next();
                }
                Thread.sleep(1);
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void number(IntConsumer printNumber) throws InterruptedException {
            while (sync >= 0) {
                if (sync == 1) {
                    printNumber.accept(index);
                    next();
                }
                Thread.sleep(1);
            }
        }
    }

    static class LRUCache146 {

        class DoubleLink {
            private DoubleLink prev;
            private DoubleLink next;

            private int key;

            private int val;

            public DoubleLink(int key, int val, DoubleLink prev, DoubleLink next) {
                this.key = key;
                this.val = val;
                this.prev = prev;
                this.next = next;
            }

            public DoubleLink getPrev() {
                return prev;
            }

            public void setPrev(DoubleLink prev) {
                this.prev = prev;
            }

            public DoubleLink getNext() {
                return next;
            }

            public void setNext(DoubleLink next) {
                this.next = next;
            }

            public int getKey() {
                return key;
            }

            public void setKey(int key) {
                this.key = key;
            }

            public int getVal() {
                return val;
            }

            public void setVal(int val) {
                this.val = val;
            }
        }

        private DoubleLink head;

        private DoubleLink tail;

        private Map<Integer, DoubleLink> cacheMap;

        private int capacity;

        public LRUCache146(int capacity) {
            if (capacity < 1) capacity = 0;
            this.capacity = capacity;
            cacheMap = new HashMap<>(capacity);
        }

        public int get(int key) {
            if (cacheMap.containsKey(key)) {
                DoubleLink now = cacheMap.get(key);
                if (now != head) {
                    DoubleLink prev = now.getPrev();
                    DoubleLink next = now.getNext();
                    now.setPrev(null);
                    now.setNext(head);
                    head.setPrev(now);
                    head = now;

                    prev.setNext(next);
                    if (next != null) {
                        next.setPrev(prev);
                    } else {
                        tail = prev;
                    }

                }
                return now.getVal();
            }
            return -1;
        }

        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            if (cacheMap.containsKey(key)) {
                DoubleLink now = cacheMap.get(key);
                now.setVal(value);
                DoubleLink prev = now.getPrev();
                DoubleLink next = now.getNext();
                if (now == head) {
                    return;
                }
                now.setPrev(null);
                now.setNext(head);
                if (head != null) {
                    head.setPrev(now);
                }
                head = now;
                cacheMap.put(key, now);

                if (prev != null) {
                    prev.setNext(next);
                }

                if (next != null) {
                    next.setPrev(prev);
                } else {
                    tail = prev;
                }
            } else {
                if (cacheMap.size() == capacity) {
                    cacheMap.remove(tail.getKey());
                    DoubleLink now = new DoubleLink(key, value, null, head);
                    cacheMap.put(key, now);
                    head.setPrev(now);
                    head = now;
                    DoubleLink prev = tail.getPrev();
                    if (prev != null) {
                        prev.setNext(null);
                    }
                    tail.setPrev(null);
                    tail = prev;
                } else {
                    DoubleLink now = new DoubleLink(key, value, null, head);
                    cacheMap.put(key, now);
                    if (head != null) {
                        head.setPrev(now);
                    }
                    head = now;
                    if (tail == null) {
                        tail = now;
                    }
                }
            }
        }
    }

    static class LFUCache460 {

        private int minCount = 0;
        private Map<Integer, DoubleLink> countMap;

        private int capacity;
        private Map<Integer, DoubleLink> cacheMap;


        public LFUCache460(int capacity) {
            if (capacity < 1) capacity = 0;
            this.capacity = capacity;
            cacheMap = new HashMap<>(capacity);
            countMap = new HashMap<>();
        }

        public int get(int key) {
            if (cacheMap.containsKey(key)) {
                DoubleLink now = cacheMap.get(key);
                removeFromCurrentCountLink(now);
                updateMinCount(now.getCount());
                addToNextCountLink(now);
                return now.getVal();
            }
            return -1;
        }

        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            if (cacheMap.containsKey(key)) {
                DoubleLink now = cacheMap.get(key);
                now.setVal(value);
                removeFromCurrentCountLink(now);
                updateMinCount(now.getCount());
                addToNextCountLink(now);
            } else {
                if (cacheMap.size() == capacity) {
                    DoubleLink minCountLinkHead = countMap.get(minCount);
                    DoubleLink minCountLinkTail = minCountLinkHead.getPrev();
                    cacheMap.remove(minCountLinkTail.key);
                    if (minCountLinkHead == minCountLinkTail) {
                        countMap.remove(minCount);
                    } else {
                        DoubleLink minCountLinkTailPrev = minCountLinkTail.getPrev();
                        minCountLinkTailPrev.setNext(minCountLinkHead);
                        minCountLinkHead.setPrev(minCountLinkTailPrev);
                    }
                }
                DoubleLink now = new DoubleLink(key, value, null, null);
                addToNextCountLink(now);
                cacheMap.put(key, now);
                minCount = now.getCount();
            }
        }

        private void updateMinCount(int count) {
            if (count == minCount && countMap.get(count) == null) {
                minCount++;
            }
        }

        private void removeFromCurrentCountLink(DoubleLink now) {
            DoubleLink head = countMap.get(now.getCount());
            DoubleLink tail = head.getPrev();
            DoubleLink prev = now.getPrev();
            DoubleLink next = now.getNext();
            if (head == tail) {
                countMap.remove(now.getCount());
            } else {
                prev.setNext(next);
                next.setPrev(prev);
                if (head == now) {
                    countMap.put(now.getCount(), next);
                }
            }
        }

        private void addToNextCountLink(DoubleLink now) {
            DoubleLink head = countMap.get(now.countAdd());
            if (head != null) {
                DoubleLink tail = head.getPrev();
                head.setPrev(now);
                tail.setNext(now);
                now.setPrev(tail);
                now.setNext(head);
            } else {
                now.setPrev(now);
                now.setNext(now);
            }
            countMap.put(now.getCount(), now);
        }

        class DoubleLink {
            private DoubleLink prev;
            private DoubleLink next;

            private int key;
            private int val;

            private int count;

            public DoubleLink(int key, int val, DoubleLink prev, DoubleLink next) {
                this.count = 0;
                this.key = key;
                this.val = val;
                this.prev = prev;
                this.next = next;
            }

            public int countAdd() {
                this.count += 1;
                return count;
            }

            public int getCount() {
                return count;
            }

            public DoubleLink getPrev() {
                return prev;
            }

            public void setPrev(DoubleLink prev) {
                this.prev = prev;
            }

            public DoubleLink getNext() {
                return next;
            }

            public void setNext(DoubleLink next) {
                this.next = next;
            }

            public int getKey() {
                return key;
            }

            public void setKey(int key) {
                this.key = key;
            }

            public int getVal() {
                return val;
            }

            public void setVal(int val) {
                this.val = val;
            }
        }
    }

    class Solution200 {
        private void dfs(char[][] grid, int r, int c) {
            int nr = grid.length;
            int nc = grid[0].length;

            if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
                return;
            }

            grid[r][c] = '0';
            dfs(grid, r - 1, c);
            dfs(grid, r + 1, c);
            dfs(grid, r, c - 1);
            dfs(grid, r, c + 1);
        }

        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) {
                return 0;
            }
            int n = grid.length;
            int m = grid[0].length;
            int res = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (grid[i][j] == '1') {
                        res++;
                        dfs(grid, i, j);
                    }
                }
            }

            return res;
        }

        public int numIslands1(char[][] grid) {
            if (grid == null || grid.length == 0) {
                return 0;
            }
            int n = grid.length;
            int m = grid[0].length;
            int res = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (grid[i][j] == '1') {
                        res++;
                        Queue<int[]> queue = new LinkedList<>();
                        queue.add(new int[]{i, j});
                        grid[i][j] = '0';
                        while (!queue.isEmpty()) {
                            int[] now = queue.remove();
                            if (now[0] != 0 && grid[now[0] - 1][now[1]] == '1') {
                                queue.add(new int[]{now[0] - 1, now[1]});
                                grid[now[0] - 1][now[1]] = '0';
                            }
                            if (now[0] != (n - 1) && grid[now[0] + 1][now[1]] == '1') {
                                queue.add(new int[]{now[0] + 1, now[1]});
                                grid[now[0] + 1][now[1]] = '0';
                            }
                            if (now[1] != 0 && grid[now[0]][now[1] - 1] == '1') {
                                queue.add(new int[]{now[0], now[1] - 1});
                                grid[now[0]][now[1] - 1] = '0';
                            }
                            if (now[1] != (m - 1) && grid[now[0]][now[1] + 1] == '1') {
                                queue.add(new int[]{now[0], now[1] + 1});
                                grid[now[0]][now[1] + 1] = '0';
                            }
                        }
                    }
                }
            }

            return res;
        }

    }

    /***********model*************/
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
    /***********model*************/

}
