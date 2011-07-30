/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Neil C Smith.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details.
 *
 * You should have received a copy of the GNU General Public License version 3
 * along with this work; if not, see http://www.gnu.org/licenses/
 *
 *
 * Please visit http://neilcsmith.net if you need additional information or
 * have any questions.
 */
package net.neilcsmith.praxis.live.pxr;

import net.neilcsmith.praxis.live.pxr.api.PraxisProperty;
import java.lang.reflect.InvocationTargetException;
import net.neilcsmith.praxis.core.ComponentType;
import net.neilcsmith.praxis.core.CallArguments;
import net.neilcsmith.praxis.live.core.api.Callback;
import java.util.Iterator;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import net.neilcsmith.praxis.core.ComponentAddress;
import net.neilcsmith.praxis.core.info.ComponentInfo;
import net.neilcsmith.praxis.live.project.api.PraxisProject;
import net.neilcsmith.praxis.live.pxr.api.ContainerProxy;
import static net.neilcsmith.praxis.live.pxr.PXRParser.*;
import net.neilcsmith.praxis.live.pxr.api.Connection;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

/**
 *
 * @author Neil C Smith (http://neilcsmith.net)
 */
class PXRBuilder {

    private final static Logger LOG = Logger.getLogger(PXRBuilder.class.getName());
    private final Project project;
    private final PXRDataObject source;
    private final RootElement root;
    private Iterator<Element> iterator;
    private Callback processCallback;
//    private Callback elementCallback;
    private PXRComponentProxy active;
    private PXRRootProxy rootProxy;
    private boolean processed;

    private PXRBuilder(Project project, PXRDataObject source, RootElement root) {
        this.project = project;
        this.source = source;
        this.root = root;
    }

    public void process(Callback callback) {
        if (callback == null) {
            throw new NullPointerException();
        }
        this.processCallback = callback;
        buildElementIterator();
        process();
    }

    private void process() {
        while (iterator.hasNext()) {
            if (!process(iterator.next())) {
                //break;
                return;
            }
        }
        if (!processed && !iterator.hasNext()) {
            processed = true;
            DefaultRootRegistry.getDefault().register(rootProxy);
            processCallback.onReturn(CallArguments.EMPTY);
        }
    }

    private boolean process(Element element) {
        if (element instanceof PropertyElement) {
            return processProperty((PropertyElement) element);
        } else if (element instanceof AttributeElement) {
            return processAttribute((AttributeElement) element);
        } else if (element instanceof ConnectionElement) {
            return processConnection((ConnectionElement) element);
        } else if (element instanceof RootElement) {
            return processRoot((RootElement) element);
        } else if (element instanceof ComponentElement) {
            return processComponent((ComponentElement) element);
        }
        processCallback.onError(CallArguments.EMPTY);
        return false;
    }

    private void processError(CallArguments args) {
        processed = true;
        processCallback.onError(args);
    }

    private boolean processProperty(PropertyElement prop) {
//        LOG.fine("Processing Property Element : " + prop.address);
//        try {
//
//            PXRHelper.getDefault().send(prop.address, CallArguments.create(prop.args), new Callback() {
//
//                @Override
//                public void onReturn(CallArguments args) {
//                    process();
//                }
//
//                @Override
//                public void onError(CallArguments args) {
//                    processError(args);
//                }
//            });
//        } catch (Exception ex) {
//            Exceptions.printStackTrace(ex);
//        }
//        return false;
//        LOG.fine("Processing Property Element : " + prop.address);
//        PXRComponentProxy cmp = findComponent(prop.address.getComponentAddress());
//        if (cmp == null) {
//            LOG.warning("Component " + prop.address.getComponentAddress() + " not found.");
//            return true;
//        }
//        PraxisProperty<?> p = cmp.getProperty(prop.address.getID());
        LOG.fine("Processing Property Element : " + prop.property);
        PXRComponentProxy cmp = findComponent(prop.component.address);
        if (cmp == null) {
            LOG.warning("Component " + prop.component.address + " not found.");
            return true;
        }
        PraxisProperty<?> p = cmp.getProperty(prop.property);
        if (p instanceof BoundArgumentProperty) {
            try {
                ((BoundArgumentProperty) p).setValue(prop.args[0], new Callback() {

                    @Override
                    public void onReturn(CallArguments args) {
                        process();
                    }

                    @Override
                    public void onError(CallArguments args) {
                        processError(args);
                    }
                });
            } catch (Exception ex) {
                LOG.warning("Couldn't set property " + prop.property);
                return true;
            }
        }
        return false;
    }

    private boolean processAttribute(AttributeElement attr) {
        PXRComponentProxy cmp = findComponent(attr.component.address);
        if (cmp != null) {
            cmp.setAttribute(attr.key, attr.value);
        }
        return true;
    }

    private boolean processConnection(ConnectionElement con) {
//        ComponentAddress address = cmp.address;
        LOG.fine("Processing Connection Element : " + con.port1 + " -> " + con.port2);
        try {
//            PXRComponentProxy parent = findComponent(con.port1.getComponentAddress().getParentAddress());
//            if (parent instanceof PXRContainerProxy) {
//                ((PXRContainerProxy) parent).connect(
//                        new ContainerProxy.Connection(con.port1, con.port2),
//                        new Callback() {
//
//                            @Override
//                            public void onReturn(CallArguments args) {
//                                process();
//                            }
//
//                            @Override
//                            public void onError(CallArguments args) {
//                                processError(args);
//                            }
//                        });
            PXRComponentProxy parent = findComponent(con.container.address);
            if (parent instanceof PXRContainerProxy) {
                ((PXRContainerProxy) parent).connect(
                        new Connection(con.component1, con.port1, con.component2, con.port2),
                        new Callback() {

                            @Override
                            public void onReturn(CallArguments args) {
                                process();
                            }

                            @Override
                            public void onError(CallArguments args) {
                                processError(args);
                            }
                        });

            } else {
                processError(CallArguments.EMPTY);
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            processError(CallArguments.EMPTY);
        }
        return false;
    }

    private boolean processRoot(RootElement root) {
        LOG.fine("Processing Root Element : " + root.address + ", Type : " + root.type);
        try {
            final ComponentAddress ad = root.address;
            final ComponentType type = root.type;
            PXRHelper.getDefault().createComponentAndGetInfo(ad, type, new Callback() {

                @Override
                public void onReturn(CallArguments args) {
                    try {
                        rootProxy = new PXRRootProxy(
                                source,
                                ad.getRootID(),
                                type,
                                ComponentInfo.coerce(args.get(0)));
                        process();
                    } catch (Exception ex) {
                        Exceptions.printStackTrace(ex);
                        onError(args);
                    }
                }

                @Override
                public void onError(CallArguments args) {
                    processError(args);
                }
            });

        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            processError(CallArguments.EMPTY);
        }
        return false;
    }

    private boolean processComponent(ComponentElement cmp) {
        LOG.fine("Processing Component Element : " + cmp.address + ", Type : " + cmp.type);
        try {
            ComponentAddress address = cmp.address;
            PXRComponentProxy parent = findComponent(address.getParentAddress());
            if (parent instanceof PXRContainerProxy) {
                String id = address.getComponentID(address.getDepth() - 1);
                ((PXRContainerProxy) parent).addChild(id, cmp.type, new Callback() {

                    @Override
                    public void onReturn(CallArguments args) {
                        process();
                    }

                    @Override
                    public void onError(CallArguments args) {
                        processError(args);
                    }
                });

            } else {
                processError(CallArguments.EMPTY);
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            processError(CallArguments.EMPTY);
        }
        return false;
    }

    private PXRComponentProxy findComponent(ComponentAddress address) {
        if (rootProxy == null) {
            return null;
        }
        if (address.getDepth() == 1 && rootProxy.getAddress().equals(address)) {
            return rootProxy;
        } else if (!rootProxy.getAddress().getRootID().equals(address.getRootID())) {
            return null;
        }

        PXRComponentProxy cmp = rootProxy;
        for (int i = 1; i < address.getDepth(); i++) {
            if (cmp instanceof PXRContainerProxy) {
                cmp = ((PXRContainerProxy) cmp).getChild(address.getComponentID(i));
            } else {
                return null;
            }
        }
        return cmp;

    }

    private synchronized void buildElementIterator() {

        if (iterator != null) {
            throw new IllegalStateException();
        }
        List<Element> elements = new LinkedList<Element>();
        addComponentElements(root, elements);
        iterator = elements.iterator();

    }

    private void addComponentElements(ComponentElement component,
            List<Element> elements) {
        elements.add(component);
        elements.addAll(Arrays.asList(component.attributes));
        elements.addAll(Arrays.asList(component.properties));
        for (ComponentElement child : component.children) {
            addComponentElements(child, elements);
        }
        elements.addAll(Arrays.asList(component.connections));
    }

    static PXRBuilder getBuilder(PraxisProject project, PXRDataObject source, PXRParser.RootElement root) {
        return new PXRBuilder(project, source, root);
    }
}