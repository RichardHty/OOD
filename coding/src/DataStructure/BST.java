package DataStructure;

/***
 * invariant: for all nodes x, if y is the left subtree of x, key(y) <= key(x). If y is the right subtree of x, key(y)>=key(x)
 */
public class BST {
    TreeNode root;
    StringBuilder treeInfo;
    int cur;
    public BST(int val){
        root = new TreeNode(val);
    }
    public void insert(int val){
        insertHelper(root,val);
    }
    public boolean isValidBST() {
        return validateHelper(root,Long.MIN_VALUE,Long.MAX_VALUE);
    }
    public int rank(int val){
        cur = 0;
        rankHelper(root,val);
        return cur;
    }


    private void rankHelper(TreeNode node, int val){
        if(node == null){
            return;
        }
        if(node.val > val){
            rankHelper(node.left,val);
            return;
        }
        cur += node.left!=null? node.left.nodeCount+1:1;
        if(node.val == val) {
            return;
        }
        rankHelper(node.right,val);
    }
    private TreeNode insertHelper(TreeNode node, int val){
        if(node == null){
            return new TreeNode(val);
        }
        node.nodeCount ++;
//        int leftHeight = node.left == null?-1:node.left.height;
//        int rightHeight = node.right == null?-1:node.right.height;
//        node.height = (leftHeight>rightHeight?leftHeight:rightHeight)+1;
//        System.out.println(node.val+": "+node.height);
        if(node.val >= val){
            node.left = insertHelper(node.left,val);
            return node;
        }
        node.right = insertHelper(node.right,val);
        return node;
    }
    private boolean validateHelper(TreeNode root, long min, long max){
        if(root == null) return true;
        if(root.val <= min || root.val >= max){
            return false;
        }
        return validateHelper(root.left, min, root.val) && validateHelper(root.right, root.val, max);
    }
    private void preorderTraversal(TreeNode node){
        if(node == null){
            return;
        }
        preorderTraversal(node.left);
        treeInfo.append(node.val);
        treeInfo.append(",");
        preorderTraversal(node.right);
    }
    public String toString(){
        treeInfo = new StringBuilder();
        preorderTraversal(root);
        return treeInfo.toString();
    }
    public void printTree(){
        System.out.println(toString());
    }

}
