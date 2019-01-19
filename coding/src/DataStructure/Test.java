package DataStructure;

public class Test {
    public static void main(String[] args){
        BST bst = new BST(49);
        bst.insert(43);
        bst.insert(46);
        bst.printTree();
        bst.insert(83);
        bst.insert(79);
        bst.insert(64);
        bst.printTree();
//        bst.insert(-1);
////        bst.insert(50);
////        bst.printTree();
        System.out.println(bst.isValidBST());
        System.out.println(bst.rank(40));


    }
}
