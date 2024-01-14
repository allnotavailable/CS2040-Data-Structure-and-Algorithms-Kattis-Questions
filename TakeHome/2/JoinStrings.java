class JoinStrings {
    public static void main (String args[]) {
        FastIO fio = new FastIO();

        int numOfStrings = fio.nextInt();
        TailedLinkedList[] listArray = new TailedLinkedList[numOfStrings + 1];
        int resultIdx = 0;

        for (int i = 1; i <= numOfStrings; i++) {
            TailedLinkedList list = new TailedLinkedList();
            list.addNode(new Node(fio.next()));
            listArray[i] = list;
        }
        if (numOfStrings != 1) {
            for (int i = 1; i < numOfStrings; i++) {
                int firstNum = fio.nextInt();
                int secondNum = fio.nextInt();
                listArray[firstNum].addList(listArray[secondNum]);
                resultIdx = firstNum;
            }
            TailedLinkedList resultList = listArray[resultIdx];
            StringBuilder output = new StringBuilder();
            output.append(resultList.head.value);

            for (int i = 0; i < resultList.size - 1; i++) {
                resultList.head = resultList.head.next;
                output.append(resultList.head.value);
            }
            System.out.println(output);
        } else {
            System.out.println(listArray[1].head.value);
        }
        fio.close();
    }
}

class Node {
    public String value;
    public Node next;

    Node(String value) {
        this.value = value;
        this.next = null;
    }

    public void add (Node next) {
        if (this.next == null) {
            this.next = next;
        } else {
            this.next.add(next);
        }
    }
}

class TailedLinkedList {
    public Node head;
    public Node tail;
    public int size;

    public void addNode(Node next) {
        next.add(this.head);
        this.head = next;
        this.tail = head;
        size++;
    }

    public void addList(TailedLinkedList list) {
        tail.next = list.head;
        tail = list.tail;
        size += list.size;
    }
}
