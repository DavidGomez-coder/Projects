package dataStructures.searchTree;

import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.tuple.*;

public class NodeB<T> {
     NodeB<T> rt;
     NodeB<T> lt;
    T elem;

    public NodeB (NodeB<T> i, T v, NodeB<T> d){
        this.lt = i;
        this.elem = v;
        this.rt = d;
    }

    public NodeB (T v){
        this(null,v,null);
    }

    @Override
    public String toString(){
        String si = (lt!=null) ? lt.toString() : "EmptyB";
        String sd = (lt!=null) ? rt.toString() : "EmptyB";
        return "NodeB(" + si + ", " + elem + ", " + sd + ")";
    }

    //suma de los valores de un árbol de enteros
    public static int suma (NodeB<Integer> ar){
        int suma = 0;
        if (ar!=null){
            suma = ar.elem + suma(ar.lt) + suma(ar.rt);
        }
        return suma;
    }

    //profundidad de un arbol
    public static <S> int prof (NodeB<S> r){
        if (r==null){
            return 0;
        }
        if (r.lt == null && r.rt == null){
            return 1;
        }else{
            return 1 + Math.max(prof(r.lt),prof(r.rt));
        }
    }

    //devuelve un árbol donde en cada nodo hay un par, el valor  y la profundidad del nodo en el árbol
    public static <S> NodeB<Tuple2<S,Integer>> anotaProf (NodeB<S> ar){
        if (ar.rt == null && ar.lt == null){
            return new NodeB<>(null, new Tuple2<>(ar.elem,1), null);
        }else{
            NodeB<Tuple2<S,Integer>> lt = anotaProf(ar.lt);
            NodeB<Tuple2<S,Integer>> rt = anotaProf(ar.rt);
            return new NodeB<>(lt, new Tuple2<>(ar.elem,1+Math.max(lt.elem._2(),rt.elem._2())),rt);
        }
    }

    //devuelve un árbol con una tripleta. La primera componente es el valor. La segunda
    //es la suma de los nodos de cada subarbol y la tercera la profundidad de cada nodo
    public static NodeB<Tuple3<Integer,Integer,Integer>> anotaSumProf (NodeB<Integer> ar){
        if (ar.lt == null && ar.rt == null){
            return new NodeB<>(null,new Tuple3<>(ar.elem,ar.elem,1),null);
        }else {
            assert ar.lt != null;
            NodeB<Tuple3<Integer,Integer,Integer>> lt = anotaSumProf(ar.lt);
            NodeB<Tuple3<Integer,Integer,Integer>> rt = anotaSumProf(ar.rt);
            return new NodeB<>(lt,new Tuple3<>(ar.elem,lt.elem._2()+rt.elem._2()+ar.elem,
                    1+Math.max(lt.elem._3(),rt.elem._3())),rt);
        }
    }

    //devuelve una lista con los valores situados en un nivel dado
    public static <S> List<S> atLevel(int n, NodeB<S> ar){
        List<S> list = new ArrayList<>();
        if (ar!=null){
            if (n==0){
                list.append(ar.elem);
            }else {
                List<S> list_izq = atLevel(n - 1, ar.lt);
                List<S> list_der = atLevel(n - 1, ar.rt);
                for (S s : list_izq){
                    list.append(s);
                }
                for (S s : list_der){
                    list.append(s);
                }
            }
        }
        return list;
    }

    public static <S> List<List<S>> pathsToB (S elem, NodeB<S> ar){
        List<List<S>> res = new ArrayList<>();
        List<S> l = new ArrayList<>();
        if (ar!=null){
            if (ar.elem.equals(elem)){
                List<S> list = new ArrayList<>();
                list.append(elem);
                res.prepend(list);
            }
            List<List<S>> li = pathsToB(elem,ar.lt);
            List<List<S>> lr = pathsToB(elem,ar.rt);
            for (List<S> s : li){
                s.prepend(ar.elem);
                res.append(s);
            }
            for (List<S> s : lr){
                s.prepend(ar.elem);
                res.append(s);
            }
        }
        return res;
    }

    //raiz-izquierda-derecha
    public static <S> List<S> preOrderB (NodeB<S> ar){
        List<S> res = new ArrayList<>();
        if (ar!=null){
            res.append(ar.elem);
            List<S> izq = preOrderB(ar.lt);
            List<S> der = preOrderB(ar.rt);
            //añadimos izq res
            for (S s : izq){
                res.append(s);
            }
            for (S s : der){
                res.append(s);
            }
        }
        return res;
    }

    //izq - raiz - der
    public static <S> List<S> inOrderB (NodeB<S> ar){
        List<S> res = new ArrayList<>();
        if (ar!=null){

            List<S> izq = preOrderB(ar.lt);
            List<S> der = preOrderB(ar.rt);
            //añadimos izq res
            for (S s : izq){
                res.append(s);
            }
            res.append(ar.elem);
            for (S s : der){
                res.append(s);
            }
        }
        return res;
    }
    //izq - der - raiz
    public static <S> List<S> postOrderB (NodeB<S> ar){
        List<S> res = new ArrayList<>();
        if (ar!=null){

            List<S> izq = preOrderB(ar.lt);
            List<S> der = preOrderB(ar.rt);
            //añadimos izq res
            for (S s : izq){
                res.append(s);
            }
            for (S s : der){
                res.append(s);
            }
            res.append(ar.elem);
        }
        return res;
    }

}
