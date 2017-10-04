# huffman-coding

This platform allows trees for Huffman coding to be constructed off of a given text to be encoded, as well as reconstructed from a previous tree.

<h2 style="margin: 10px">Construction</h2>
The Tree constructor takes in three arguments: 
<br><code>public Tree(TreeType type, String filename, Boolean fromSerialization)</code><br>

- Firstly, the type, which denotes whether 0 is attached to the left child and 1 the right, or vice versa

- Secondly, a filename, which can either contain the text to be encoded, or serialization data from a previous tree.

- And thirdly, a boolean which indicates which option the second argument is.

Using this information, the given file is read and organized in accordance.

If the data is text to be encoded, a priority queue is constructed from analyzing character frequency, and the tree is built from that.

If the data is a serialization, that data is used to construct the tree directly

Once the tree exists, a hashmap of characters to binary representations (<code>binEquiv</code>) is created by searching the entire tree. The objective behind this approach is to search the tree only once, so that when encoding, lookup is constant time using the hashmap instead of having to repeatedly search the tree.

<h2 style="margin: 10px">Encoding</h2>
To encode, each character in the input data is searched in the <code>binEquiv</code> hashmap and its binary representation is written to the output file.

<h2 style="margin: 10px">Decoding</h2>
To decode, the binary input is followed down the tree until a leaf node is reached, at which point the node content is written to the output file and the next bit starts over at the root of the tree.

<h2 style="margin: 10px">Serialization</h2>
Serialization is accomplished through a breadth-first search of the entire tree. In designing this approach to recording the structure of the tree, the goal was to write as little information as possible. Instead of using a depth-first search approach or even writing each triplet of nodes in the form "parent, child, child", I chose BFS as it only requires us to write as much information as there are nodes.

Sample serialization data looks like this:
<br>
<pre>
##
a
\n
c
</pre>

The <code>\#\#</code> symbol represents the null character (\u0000), and <code>\n</code> represents newline.

The root node is always omitted from serialization, since it will always be void of content. This data would be interpreted as:
- Root has two children, <code>null</code> and <code>'a'</code>, left and right respectively
- Root's left child, <code>null</code>, has two children <code>'\n'</code> and <code>'c'</code>, again left and right

This configuration, in a <code>_0LEFT_1RIGHT</code> tree, would yield binary representations of each character as follows,
<pre>
'a':    1
'\n':   00
'c':    01
</pre>
which could then be used to encode.
