package com.leet;


/**
 * @link https://leetcode-cn.com/problems/design-skiplist/
 */
public class Skiplist {

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
