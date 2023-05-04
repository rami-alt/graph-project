

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AdjacencyMapGraph<V, E> implements Graph<V, E> {

	private boolean isDirected;
	private List<Vertex<V>> vertices = new ArrayList<>();
	private List<Edge<E>> edges = new ArrayList<>();

	/**
	 * Constructs an empty graph. The parameter determines whether this is an
	 * undirected or directed graph.
	 */
	public AdjacencyMapGraph(boolean directed) {
		isDirected = directed;
	}

	@Override
	public int numVertices() {
		return vertices.size();
	}

	@Override
	public int numEdges() {

		return edges.size();
	}

	@Override
	public Iterable<Vertex<V>> vertices() {
		return vertices;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		return edges;
	}

	@Override
	public int outDegree(Vertex<V> v) throws IllegalArgumentException {

		InnerVertex<V> ver = validate(v);
		return ver.getOutgoing().size();
	}

	@Override
	public int inDegree(Vertex<V> v) throws IllegalArgumentException {

		InnerVertex<V> ver = validate(v);
		return ver.getIncoming().size();
	}

	@Override
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> ver = validate(v);
		return ver.outgoing.values();
	}

	@Override
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> ver = validate(v);
		return ver.incoming.values();
	}

	@Override
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> origin = validate(u);
		return origin.getOutgoing().get(v);
	}

	@Override
	public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		return edge.getEndpoints();
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		Vertex<V>[] endpoints = edge.getEndpoints();
		if (endpoints[0] == v)
			return endpoints[1];
		else if (endpoints[1] == v)
			return endpoints[0];
		else
			throw new IllegalArgumentException("v is not incident to this edge");

	}

	@Override
	public Vertex<V> insertVertex(V element) {
		InnerVertex<V> v = new InnerVertex<>(element, isDirected);
		vertices.add(v);
		return v;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {

		if (getEdge(u, v) == null) {
			InnerEdge<E> e = new InnerEdge<>(u, v, element);
			edges.add(e);
			InnerVertex<V> origin = validate(u);
			InnerVertex<V> dest = validate(v);
			origin.getOutgoing().put(v, e);
			dest.getIncoming().put(u, e);
			return e;
		} else
			throw new IllegalArgumentException("Edge from u to v exists");
				}

	@Override
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);

		for (Edge<E> e : vert.getOutgoing().values())
			removeEdge(e);
		for (Edge<E> e : vert.getIncoming().values())
			removeEdge(e);

		vertices.remove(vert);

	}

	@Override
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		Vertex<V>[] verts = edge.getEndpoints();

		((InnerVertex<V>) verts[0]).getOutgoing().remove(verts[1]);
		((InnerVertex<V>) verts[1]).getIncoming().remove(verts[0]);
		edges.remove(edge);

	}

	// ---------------- nested Vertex class ----------------
	/** A vertex of an adjacency map graph representation. */
	private class InnerVertex<V> implements Vertex<V> {
		private V element;
		private Map<Vertex<V>, Edge<E>> outgoing, incoming;

		/** Constructs a new InnerVertex instance storing the given element. */
		public InnerVertex(V elem, boolean graphIsDirected) {
			element = elem;
			outgoing = new HashMap<>();
			if (graphIsDirected)
				incoming = new HashMap<>();
			else
				incoming = outgoing; // if undirected, alias outgoing map
		}

		/** Returns the element associated with the vertex. */
		public V getElement() {
			return element;
		}

		/** Returns reference to the underlying map of outgoing edges. */
		public Map<Vertex<V>, Edge<E>> getOutgoing() {
			return outgoing;
		}

	/** Returns reference to the underlying map of incoming edges. */
		public Map<Vertex<V>, Edge<E>> getIncoming() {
			return incoming;
		}
	} // ------------ end of InnerVertex class ------------
     //
	// ---------------- nested InnerEdge class ----------------
   //** An edge between two vertices. */
	private class InnerEdge<E> implements Edge<E> {
		private E element;
		private Vertex<V>[] endpoints;

		@SuppressWarnings({ "unchecked" })
		/** Constructs InnerEdge instance from u to v, storing the given element. */
		public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
			element = elem;
			endpoints = (Vertex<V>[]) new Vertex[] { u, v }; // array of length 2
		}

		/** Returns the element associated with the edge. */
		public E getElement() {
			return element;
		}

		/** Returns reference to the endpoint array. */
		public Vertex<V>[] getEndpoints() {
			return endpoints;
		}

	} // ------------ end of InnerEdge class ------------

	private InnerVertex<V> validate(Vertex<V> v) {
		if (!(v instanceof InnerVertex))
			throw new IllegalArgumentException("Invalid vertex");
		InnerVertex<V> vert = (InnerVertex<V>) v;
		return vert;
	}

	private InnerEdge<E> validate(Edge<E> e) {
		if (!(e instanceof InnerEdge))
			throw new IllegalArgumentException("Invalid edge");
		InnerEdge<E> edge = (InnerEdge<E>) e;
		return edge;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Vertex<V> v : vertices) {
			sb.append("Vertex " + v.getElement() + "\n");
			if (isDirected) {
				sb.append(" [outgoing]");
			}
			sb.append(" " + outDegree(v) + " adjacent vertices.");
			for (Edge<E> e : outgoingEdges(v)) {
				sb.append(String.format(" (%s,%s)", opposite(v, e).getElement(), e.getElement()));
			}
			sb.append("\n");
			if (isDirected) {
				sb.append(" [incoming]");
				sb.append(" " + inDegree(v) + " adjacent vertices.");
				for (Edge<E> e : incomingEdges(v)) {
					sb.append(String.format(" (%s,%s)", opposite(v, e).getElement(), e.getElement()));
				}
				sb.append("\n");
			}
		}

		return sb.toString();
	}

}