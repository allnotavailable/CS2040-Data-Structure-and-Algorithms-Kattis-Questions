import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

class Nicknames {
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        HashMap<Character, AVL> map = new HashMap<Character, AVL>();
        String numOfNamesInput = br.readLine();
        int numOfNames = Integer.parseInt(numOfNamesInput);

        for (int i = 0; i < numOfNames; i++) {
            String name = br.readLine();
            char firstLetter = name.charAt(0);
            if (!map.containsKey(firstLetter)) {
                AVL avl = new AVL();
                avl.insert(name);
                map.put(firstLetter, avl);
            } else {
                map.get(firstLetter).insert(name);
            }
        }

        String numOfNicknamesInput = br.readLine();
        int numOfNicknames = Integer.parseInt(numOfNicknamesInput);

        for (int i = 0; i < numOfNicknames; i++) {
            String nickname = br.readLine();
            char firstLetter = nickname.charAt(0);
            int matches = 0;
            
            if (map.containsKey(firstLetter)) {
                matches = map.get(firstLetter).isValid(nickname);
            } else {
                matches = 0;
            }
            pw.write(matches + "\n");
        }
        pw.close();    
    }    
}

class Vertex {
    
    public Vertex parent;
    public Vertex left;
    public Vertex right;
    public String key;
    public int height;
    public int size;

    Vertex(String key) {
        this.parent = null;
        this.left = null;
        this.right = null;
        this.key = key;
        this.height = 0;
        this.size = 1;
    }
}

class AVL {
    Vertex root;

    AVL() {
        this.root = null;
    }

    public void insert (String key) {
        this.root = insert(this.root, key);
    }

    public Vertex insert (Vertex p, String key) {
        if (p == null) {
            return new Vertex(key);
        }
        if (key.compareTo(p.key) < 0) {
            p.left = insert(p.left, key);
            p.left.parent = p;
        } else {
            p.right = insert(p.right, key);
            p.right.parent = p;
        }
        updateHeight(p);
        updateSize(p);
        return needRotation(p);
    }

    public int getHeight(Vertex p) {
        if (p == null) {
            return -1;
        } else {
            return p.height;
        }
    }

    public int getSize(Vertex p) {
        if (p == null) {
            return 0;
        } else {
            return p.size;
        }
    }

    public void updateHeight(Vertex p) {
        if (p != null) {
            p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        }
    }

    public void updateSize(Vertex p) {
        if (p != null) {
            p.size = getSize(p.left) + getSize(p.right) + 1;
        }
    }

    public int getBalanceFactor(Vertex p) {
        if (p == null) {
            return 0; 
        } else {
            return getHeight(p.left) - getHeight(p.right);
        }
    }

    public Vertex needRotation(Vertex p) {
        if (getBalanceFactor(p) > 1) {
            if (getBalanceFactor(p.left) < 0) {
                p.left = rotateLeft(p.left);
            }
            p = rotateRight(p);
        } else if (getBalanceFactor(p) < -1){
            if (getBalanceFactor(p.right) > 0) {
                p.right = rotateRight(p.right);
            }
            p = rotateLeft(p);
        }
        return p;
    }

    public Vertex rotateRight(Vertex p) {
        if (p.left != null) {
            Vertex q = p.left;
            p.left = q.right;
            if (q.right != null) {
                q.right.parent = p;
            }
            q.parent = p.parent;
            if (p.parent == null) {
                this.root = q;
            } else if (p.parent.left == p) {
                p.parent.left = q;
            } else {
                p.parent.right = q;
            }
            q.right = p;
            p.parent = q;
            updateHeight(p);
            updateHeight(q);
            updateSize(p);
            updateSize(q);
            
            return q;
        }
        return p;
    }
    
    public Vertex rotateLeft(Vertex p) {
        if (p.right != null) {
            Vertex q = p.right;
            p.right = q.left;
            if (q.left != null) {
                q.left.parent = p;
            }
            q.parent = p.parent;
            if (p.parent == null) {
                this.root = q;
            } else if (p == p.parent.left) {
                p.parent.left = q;
            } else {
                p.parent.right = q;
            }
            q.left = p;
            p.parent = q;

            updateHeight(p);
            updateHeight(q);
            updateSize(p);
            updateSize(q);

            return q;
        }
        return p;
    }

    public int isValid(String key) {
        Vertex firstValidVertex = findFirstValidVertex(this.root, key);
        if (firstValidVertex == null) {
            return 0;
        }
        return checkLeft(firstValidVertex.left, key) + checkRight(firstValidVertex.right, key) + 1;
    }

    public Vertex findFirstValidVertex(Vertex p, String key) {
        if (p == null) {
            return null;
        }
        String currentKey = p.key;
        if (currentKey.indexOf(key) == 0) {
            return p;
        }
        int compareTo = key.compareTo(currentKey);
        if (compareTo > 0) {
            return findFirstValidVertex(p.right, key);
        } else {
            return findFirstValidVertex(p.left, key);
        }
    }

    int checkLeft(Vertex p, String key) {
        if (p == null) {
            return 0;
        }
        String currentKey = p.key;
        if (currentKey.indexOf(key) == 0) {
            return checkLeft(p.left, key) + getSize(p.right) + 1;
        } else {
            return checkLeft(p.right, key);
        }
    }

    int checkRight(Vertex p, String key) {
        if (p == null) {
            return 0;
        }
        String currentKey = p.key;
        if (currentKey.indexOf(key) == 0) {
            return checkRight(p.right, key) + getSize(p.left) + 1;
        } else {
            return checkRight(p.left, key);
        }
    }

}