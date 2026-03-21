package com.example.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import com.example.model.Question;

public class QList<T extends Question> implements List<T> {
    private T[] questions;
    private final Class<T> type;
    private int size;

    @SuppressWarnings("unchecked")
    public QList() {
        this.questions = (T[]) new Question[10];
        type = (Class<T>) questions.getClass().getComponentType();
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
       return size == 0;
    }

    public boolean isFull() {
        return size == questions.length;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
        if (!type.isInstance(o))
            return false;

        for (int i = 0; i < size; i++) {
            if (questions[i].equals((T) o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        class QuestionIterator implements Iterator<T> {
            private int currentIndex;

            public QuestionIterator() {
                this.currentIndex = 0;
            }

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return questions[currentIndex++];
            }
        }
        return new QuestionIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        System.arraycopy(questions, 0, arr, 0, size);
        return arr;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            @SuppressWarnings("unchecked")
            T[] newArray = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
            System.arraycopy(questions, 0, newArray, 0, size);
            return newArray;
        }
        System.arraycopy(questions, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(T e) {
        if (isFull()) {
            resize();
        }
        questions[size++] = e;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) {
        if (!contains(o))
            return false;
        int index = find((T) o);
        for (int i = index; i < size - 1; i++) {
            questions[i] = questions[i + 1];
        }
        questions[index] = (T)o;
        size--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.getClass().componentType() != type)
            return false;
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (!checkCollectionType(c))
            throw new IllegalArgumentException("Collection type does not match QList type");

        for (T element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (!checkCollectionType(c))
            return false;

        for (T element : c) {
            add(index++, element);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (!containsAll(c))
            return false;
        for (Object o : c) {
            remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(questions[i])) {
                remove(questions[i]);
                modified = true;
            }
        }
        return modified;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        questions = (T[]) new Question[size];
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return questions[index];
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T oldElement = questions[index];
        questions[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (isFull()) {
            resize();
        }
        for (int i = size; i > index; i--) {
            questions[i] = questions[i - 1];
        }
        questions[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T removedElement = questions[index];
        for (int i = index; i < size - 1; i++) {
            questions[i] = questions[i + 1];
        }
        questions[--size] = null;
        return removedElement;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int indexOf(Object o) {
        if (!type.isInstance(o))
            return -1;
        for (int i = 0; i < size; i++) {
            if (questions[i].equals((T) o)) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int lastIndexOf(Object o) {
        if (!type.isInstance(o))
            return -1;
        for (int i = size - 1; i >= 0; i--) {
            if (questions[i].equals((T) o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        class QuestionListIterator implements ListIterator<T> {
            private int currentIndex;

            public QuestionListIterator() {
                this.currentIndex = 0;
            }

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return questions[currentIndex++];
            }

            @Override
            public boolean hasPrevious() {
                return currentIndex > 0;
            }

            @Override
            public T previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return questions[--currentIndex];
            }

            @Override
            public int nextIndex() {
                return currentIndex;
            }

            @Override
            public int previousIndex() {
                return currentIndex - 1;
            }

            @Override
            public void remove() {
                if (currentIndex <= 0 || currentIndex > size) {
                    throw new IllegalStateException();
                }
                QList.this.remove(--currentIndex);
            }

            @Override
            public void set(T e) {
                if (currentIndex <= 0 || currentIndex > size) {
                    throw new IllegalStateException();
                }
                questions[currentIndex - 1] = e;
            }

            @Override
            public void add(T e) {
                QList.this.add(currentIndex++, e);
            }
        }
        return new QuestionListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        class QuestionListIterator implements ListIterator<T> {
            private int currentIndex;

            public QuestionListIterator(int index) {
                if (index < 0 || index > size) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
                }
                this.currentIndex = index;
            }

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return questions[currentIndex++];
            }

            @Override
            public boolean hasPrevious() {
                return currentIndex > 0;
            }

            @Override
            public T previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return questions[--currentIndex];
            }

            @Override
            public int nextIndex() {
                return currentIndex;
            }

            @Override
            public int previousIndex() {
                return currentIndex - 1;
            }

            @Override
            public void remove() {
                if (currentIndex <= 0 || currentIndex > size) {
                    throw new IllegalStateException();
                }
                QList.this.remove(--currentIndex);
            }

            @Override
            public void set(T e) {
                if (currentIndex <= 0 || currentIndex > size) {
                    throw new IllegalStateException();
                }
                questions[currentIndex - 1] = e;
            }

            @Override
            public void add(T e) {
                QList.this.add(currentIndex++, e);
            }
        }
        return new QuestionListIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("From Index: " + fromIndex + ", To Index: " + toIndex + ", Size: " + size);
        }
        QList<T> sublist = new QList<>();
        Iterator<T> it = listIterator(fromIndex);
        while(it.hasNext() && fromIndex < toIndex) {
            sublist.add(it.next());
            fromIndex++;
        }
        return sublist;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] arr = (T[]) new Question[questions.length * 2];
        int index = 0;
        for (T question : questions) {
            arr[index++] = question;
        }
        questions = arr;
    }

    private int find(T t){
        int index = -1;
        if (t == null){return index;}
        while (index++ < size){
            if (t.compareTo(questions[index]) == 0)
                return index;
        }
        return -1;
    }

    boolean checkCollectionType(Collection<?> c) {
        return type.isInstance(c.toArray());
    }
    


}
